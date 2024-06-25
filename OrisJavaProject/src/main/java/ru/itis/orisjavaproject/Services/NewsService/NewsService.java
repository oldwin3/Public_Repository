package ru.itis.orisjavaproject.Services.NewsService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.orisjavaproject.Entities.News;
import ru.itis.orisjavaproject.Repositories.NewsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;


    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public Optional<News> getNewsById(UUID id) {
        return newsRepository.findById(id);
    }

    public News saveNews(News news) {
        return newsRepository.save(news);
    }

    public void updateNews(News news) {
        newsRepository.update(news.getId(), news.getTitle(), news.getContent());
    }

    public void deleteNews(UUID id) {
        newsRepository.deleteById(id);
    }
}