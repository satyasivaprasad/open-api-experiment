package org.example.openapi.demo.controller;

import org.example.openapi.demo.api.BooksApi;
import org.example.openapi.demo.converters.LocalizableLinkedList;
import org.example.openapi.demo.dto.Book;
import org.example.openapi.demo.dto.BooksResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController implements BooksApi {

    private static final List<Map<String, Map<String, String>>> BOOKS = List.of(Map.of("id", Map.of("value", "1"), "title", Map.of("en", "The Alchemist", "fr", "L'Alchimiste")), Map.of("id", Map.of("value", "2"), "title", Map.of("en", "Harry Potter and the Sorcerer's Stone", "fr", "Harry Potter à l'école des sorciers")), Map.of("id", Map.of("value", "3"), "title", Map.of("en", "One Hundred Years of Solitude", "fr", "Cent ans de solitude")));

    @Override
    public BooksResponse getBooks() {

        List<Book> defaultBooks = getDefaultBooks();
        LocalizableLinkedList<Book> localizedBooks = new LocalizableLinkedList<>(defaultBooks);

        // Return response
        return new BooksResponse().books(localizedBooks);
    }

    public List<Book> getDefaultBooks() {
        return BOOKS.stream()
                .map(book -> {
                    // Extract "id" from the map
                    String id = book.get("id").get("value");
                    String title = book.get("title").getOrDefault("en", "No title available");

                    // Create a Book object with the id and title
                    return new Book(id, title);
                })
                .collect(Collectors.toList());  // Collect the results into a list
    }
}

