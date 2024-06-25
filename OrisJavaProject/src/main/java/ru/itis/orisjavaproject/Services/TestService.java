package ru.itis.orisjavaproject.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.itis.orisjavaproject.Entities.BookEntitys.Author;
import ru.itis.orisjavaproject.Entities.BookEntitys.Book;
import ru.itis.orisjavaproject.Entities.BookEntitys.MainSubject;
import ru.itis.orisjavaproject.Entities.BookEntitys.Subject;
import ru.itis.orisjavaproject.Entities.EntityesForMoodTest.*;
import ru.itis.orisjavaproject.Entities.User;
import ru.itis.orisjavaproject.Repositories.*;
import ru.itis.orisjavaproject.Repositories.Critria.CustomRepository;


import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {
    private final UserRepository userRepository;
    private final CustomRepository customRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final SubjectRepository subjectRepository;
    private final MainSubjectRepository mainSubjectRepository;
    private final ObjectMapper objectMapper;
    private final QuestionRepository questionRepository;
    private final MoodScoreRepository moodScoreRepository;

    public void addDataToDatabase() {
        List<Question> questions = Arrays.asList(
                createQuestion("How do you rate your work today?",
                        Arrays.asList("Good", "Satisfactory", "Bad", "Very bad", "Excellent")),
                createQuestion("How do you rate your day?",
                        Arrays.asList("Good", "Satisfactory", "Bad", "Very bad", "Excellent")),
                createQuestion("How do you rate your health today?",
                        Arrays.asList("Good", "Satisfactory", "Bad", "Very bad", "Excellent")),
                createQuestion("How do you rate your life?",
                        Arrays.asList("Good", "Satisfactory", "Bad", "Very bad", "Excellent")),
                createQuestion("How do you rate your love?",
                        Arrays.asList("Good", "Satisfactory", "Bad", "Very bad", "Excellent"))
        );
        questionRepository.saveAll(questions);

        Map<String, Double> moodScores = Map.of(
                "Positive mood", 0.0,
                "Intriguing mood", 0.0,
                "Dark mood", 0.0,
                "Enlightened mood", 0.0,
                "Scientific mood", 0.0
        );
        List<MoodScore> moodScoreList = moodScores.entrySet().stream()
                .map(entry -> {
                    MoodScore moodScore = new MoodScore();
                    moodScore.setMood(entry.getKey());
                    moodScore.setScore(entry.getValue());
                    return moodScore;
                })
                .collect(Collectors.toList());
        moodScoreRepository.saveAll(moodScoreList);
    }

    private Question createQuestion(String text, List<String> optionsText) {
        Question question = new Question();
        question.setText(text);
        List<Option> options = optionsText.stream()
                .map(optionText -> {
                    Option option = new Option();
                    option.setText(optionText);
                    option.setQuestion(question);
                    return option;
                })
                .collect(Collectors.toList());
        question.setOptions(options);
        return question;
    }

    public List<Question> getQuestions() {
        return questionRepository.findAllWithOptions();
    }

    public void saveBookToLibrary(UUID userid, UUID bookToSave) {
        userRepository.addBookToUser(userid, bookToSave);
    }

    public boolean bookSaved(UUID userId, String bookTitle) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Book> bookOptional = bookRepository.findByTitle(bookTitle);
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                return user.getBooks().contains(book);
            }
        }
        return false;
    }


    public Map<String, Double> determineMood(List<Option> selectedOptions) {
        Map<String, Double> moodScores = new HashMap<>();
        moodScores.put("Positive mood", 0.0);
        moodScores.put("Intriguing mood", 0.0);
        moodScores.put("Dark mood", 0.0);
        moodScores.put("Enlightened mood", 0.0);
        moodScores.put("Scientific mood", 0.0);

        for (Option selectedOption : selectedOptions) {
            String optionText = selectedOption.getText();
            switch (optionText) {
                case "Good":
                    moodScores.compute("Positive mood", (k, v) -> v != null ? v + 1.0 : 1.0);
                    break;
                case "Satisfactory":
                    moodScores.compute("Intriguing mood", (k, v) -> v != null ? v + 1.0 : 1.0);
                    break;
                case "Bad":
                    moodScores.compute("Dark mood", (k, v) -> v != null ? v + 1.0 : 1.0);
                    break;
                case "Very bad":
                    moodScores.compute("Enlightened mood", (k, v) -> v != null ? v + 1.0 : 1.0);
                    break;
                case "Excellent":
                    moodScores.compute("Scientific mood", (k, v) -> v != null ? v + 1.0 : 1.0);
                    break;
                default:
                    break;
            }
        }

        return moodScores;
    }

    @Transactional
    public List<Book> returnBooksForUser(Map<String, Double> mood, UUID userID) {
        Map<String, Double> sortedMap3 = mood.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        log.info("Mood names: {}", sortedMap3);

        List<String> subjectNames = mainSubjectRepository.findNamesByMood(getFirstKey(sortedMap3));
        List<Subject> subjects = subjectRepository.findByNameIn(subjectNames);
        log.info("Subject names: {}", subjectNames);

        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found"));
        List<Subject> subjectNames2 = customRepository.findMostFrequentSubjectsByUser
                (user, PageRequest.of(0, 10));
        log.info("Subject names2: {}", subjectNames2);


        List<Author> mostFrequentAuthors = customRepository.findMostFrequentAuthorsByUser(user,
                PageRequest.of(0, 10));
        log.info("Most frequent authors: {}", mostFrequentAuthors);


        List<Book> allBooks = bookRepository.findAll();
        Double a = mood.get(getFirstKey(sortedMap3));

        Map<Book, Double> bookScores = new HashMap<>();
        for (Book book : allBooks) {
            if (!user.getBooks().contains(book)) {
                boolean authorMatches = mostFrequentAuthors.stream()
                        .anyMatch(author -> book.getAuthors().contains(author));

                double x = authorMatches ? 1 : 0;

                double y = book.getSubjects().stream()
                        .filter(subjects::contains)
                        .count();

                double z = book.getSubjects().stream()
                        .filter(subjectNames2::contains)
                        .count();

                double score = x * 3 + (((y / a) * 10.0 + z) / 2.0) * 0.7;
                bookScores.put(book, score);
            }
        }
        List<Book> recommendedBooks = bookScores.entrySet().stream()
                .sorted(Map.Entry.<Book, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        return recommendedBooks;
    }

    @SneakyThrows
    public BookForParsing getBooksBySubject(UUID userId) {
        WebClient webClient = WebClient.create("https://openlibrary.org");
        List<MainSubject> mainSubjects = mainSubjectRepository.findAll();
        int randomIndex = (int) (Math.random() * mainSubjects.size());
        String subjectName = mainSubjects.get(randomIndex).getName();
        log.info("Subject name: {}", subjectName);

        String modifiedSubjectName = subjectName.toLowerCase().replace(' ', '_');

        String url = "subjects/" + modifiedSubjectName + ".json";

        String response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        SubjectResponse subjectResponse = null;
            subjectResponse = objectMapper.readValue(response, SubjectResponse.class);

        int i = 0;
        List<Book> books = userRepository.findAllBooksByUserId(userId);
        for (Book book :books) {
            for (BookForParsing bookForParsing: subjectResponse.getWorks()) {
                if(book.getTitle().equals(bookForParsing.getTitle())){
                    i++;
                }
                else {
                    BookForParsing bookForParsings = subjectResponse.getWorks().get(i);
                    processBooksAsync(bookForParsings);
                    return bookForParsings;
                }
            }

        }

        return null;
    }

    @Async
    @SneakyThrows
    @Transactional
    public void processBooksAsync(BookForParsing bookForParsing) {
        if(bookRepository.findByTitle(bookForParsing.getTitle()).isEmpty()){
            Book book = new Book();
            book.setTitle(bookForParsing.getTitle());

            Set<Author> authors = new HashSet<>();
            for (Author authorForParsing : bookForParsing.getAuthors()) {
                // not optimize bd (2 authors with books in api)
                List<Author> authorList = authorRepository.findByNameList(authorForParsing.getName());
                if (authorList.size() == 1 || authorList.size() == 0) {
                    Author author = authorRepository.findByName(authorForParsing.getName())
                            .orElseGet(() -> authorRepository.save(authorForParsing));
                    authors.add(author);
                } else if (authorList.size() > 1) {
                    Author author = authorRepository.findFirstAuthorWithBooksByName(authorForParsing.getName())
                            .orElseGet(() -> authorRepository.save(authorForParsing));
                    authors.add(author);
                }
            }
            book.setAuthors(authors);


            List<String> subjectNames = Arrays.stream(bookForParsing.getSubject()).toList();
            List<Subject> existingSubjects = subjectRepository.findByNameIn(subjectNames);
            List<String> newSubjectNames = subjectNames.stream()
                    .filter(name -> existingSubjects.stream().noneMatch(subject -> subject.getName().equals(name)))
                    .collect(Collectors.toList());
            List<Subject> newSubjects = newSubjectNames.stream()
                    .map(Subject::new)
                    .collect(Collectors.toList());
            subjectRepository.saveAll(newSubjects);
            Set<Subject> subjects = new HashSet<>(existingSubjects);
            subjects.addAll(newSubjects);

            book.setSubjects(subjects);

            Optional<Book> existingBook = bookRepository.findByTitle(book.getTitle());
            if (existingBook.isEmpty()) {
                bookRepository.save(book);
            }
        }
    }




    public static String getFirstKey(Map<String, Double> sortedMap) {
        return sortedMap.keySet().iterator().next();
    }

}