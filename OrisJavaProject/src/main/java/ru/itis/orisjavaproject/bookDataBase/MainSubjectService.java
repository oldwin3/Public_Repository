package ru.itis.orisjavaproject.bookDataBase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.orisjavaproject.Entities.BookEntitys.MainSubject;
import ru.itis.orisjavaproject.Entities.BookEntitys.Subject;
import ru.itis.orisjavaproject.Repositories.MainSubjectRepository;
import ru.itis.orisjavaproject.Repositories.SubjectRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainSubjectService {

    private final MainSubjectRepository mainSubjectRepository;
    private final SubjectRepository subjectRepository;


    public void addGenresToMainSubject() {
        List<String> genres = Arrays.asList(
                "Romance", "Fantasy", "Science Fiction", "Horror", "Thriller", "Mystery",
                "Action", "Historical Fiction", "War Novel", "Biography", "Classic Literature",
                "Contemporary Fiction", "Poetry", "Drama", "Folklore", "Supernatural",
                "Psychological Thriller", "Police Procedural", "Foreign Literature", "Russian Classics",
                "Children's Literature", "Humor", "Satire", "Magical Realism", "Alternate History",
                "Dark Fantasy", "Paranormal Romance", "Graphic Novels", "Military Fantasy",
                "Fantasy Mystery", "Science Fantasy", "Historical Fantasy", "Fantasy Supernatural",
                "Fantasy Folklore", "Fantasy Poetry", "Fantasy Drama", "Fantasy Humor",
                "Fantasy Satire", "Fantasy Magical Realism", "Fantasy Alternate History",
                "Urban Fantasy", "Gothic Fiction", "Steampunk", "Cyberpunk", "Dystopian",
                "Apocalyptic", "Post-apocalyptic", "Adventure", "Crime Fiction", "Young Adult Fiction", "Literary Fiction", "Speculative Fiction", "Time Travel",
                "Space Opera", "Hard Science Fiction", "Soft Science Fiction", "Techno-thriller", "Legal Thriller",
                "Medical Thriller", "Spy Thriller", "Political Thriller", "Erotic Literature", "Inspirational Fiction",
                "Christian Fiction", "Islamic Literature", "Jewish Literature", "Mythology", "Fairy Tales",
                "Legends", "Myths and Legends", "Detective Fiction", "Cozy Mystery", "Hard-boiled Mystery",
                "Legal Drama", "Medical Drama", "Political Drama", "Family Saga", "Saga Novel",
                "Western", "Adventure Novel", "Travel Literature", "Nature Writing", "Environmental Literature",
                "Eco-fiction", "Eco-thriller", "Eco-mystery", "Eco-fantasy", "Eco-horror",
                "Eco-science fiction", "Eco-dystopian", "Eco-apocalyptic", "Eco-post-apocalyptic", "Eco-adventure",
                "Eco-crime fiction", "Eco-young adult fiction", "Eco-literary fiction", "Eco-speculative fiction",
                "Eco-time travel", "Eco-space opera", "Eco-hard science fiction"
        );

        for (String genre : genres) {
            Subject subject = subjectRepository.findByName(genre).orElse(null);
            if (subject != null) {
                MainSubject mainSubject = new MainSubject();
                mainSubject.setId(subject.getId());
                mainSubject.setName(subject.getName());
                mainSubjectRepository.save(mainSubject);
            }
        }
    }
    public void updateMoodsForExistingGenres() {

        Map<String, List<String>> moodGroups = Map.of(
                "Positive mood", Arrays.asList("Romance", "Fantasy", "Science Fantasy",
                        "Children's Literature", "Humor", "Fairy Tales", "Legends", "Cozy Mystery", "Family Saga",
                        "Travel Literature"),
                "Intriguing mood", Arrays.asList("Thriller", "Mystery", "Action", "Historical Fiction",
                        "Police Procedural", "Detective Fiction", "Adventure", "Crime Fiction", "Young Adult Fiction",
                        "Political Thriller"),
                "Dark mood", Arrays.asList("Horror", "Dark Fantasy", "Gothic Fiction", "Steampunk", "Cyberpunk",
                        "Dystopian", "Apocalyptic", "Post-apocalyptic", "Erotic Literature", "Eco-horror"),
                "Enlightened mood", Arrays.asList("Biography", "Classic Literature", "Contemporary Fiction",
                        "Poetry", "Drama", "Folklore", "Supernatural", "Magical Realism", "Literary Fiction",
                        "Speculative Fiction"),
                "Scientific mood", Arrays.asList("Science Fiction", "Paranormal Romance", "Graphic Novels",
                        "Historical Fantasy", "Urban Fantasy", "Time Travel", "Space Opera", "Hard Science Fiction",
                        "Soft Science Fiction", "Eco-science fiction", "Eco-fiction")
        );

        for (Map.Entry<String, List<String>> entry : moodGroups.entrySet()) {
            String mood = entry.getKey();
            List<String> genres = entry.getValue();

            List<MainSubject> existingGenres = mainSubjectRepository.findByNameIn(genres);
            for (MainSubject mainSubject : existingGenres) {
                mainSubject.setMood(mood);
            }
            mainSubjectRepository.saveAll(existingGenres);
        }
    }
}