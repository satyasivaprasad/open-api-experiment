package org.example.openapi.demo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import lombok.extern.slf4j.Slf4j;
import org.example.openapi.demo.Localizable;
import org.example.openapi.demo.dto.Book;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.util.*;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@JsonComponent
public class LocalizableCollectionSerializer<E extends Localizable> extends JsonSerializer<Collection<E>> {

    // Static sample list of books (the BOOKS list)
    private static final Map<String, Map<String, Map<String, String>>> LOCALIZABLE_MAP = new HashMap<>();

    static {
        Map<String, Map<String, String>> book1 = new HashMap<>();
        book1.put("id", Map.of("value", "1"));
        book1.put("title", Map.of(
                "en", "The Alchemist",
                "fr", "L'Alchimiste",
                "es", "El Alquimista",
                "ja", "アルケミスト",
                "ko", "연금술사",
                "it", "L'Alchimista",
                "de", "Der Alchimist"
        ));
        LOCALIZABLE_MAP.put("1", book1);

        Map<String, Map<String, String>> book2 = new HashMap<>();
        book2.put("id", Map.of("value", "2"));
        book2.put("title", Map.of(
                "en", "Harry Potter and the Sorcerer's Stone",
                "fr", "Harry Potter à l'école des sorciers",
                "es", "Harry Potter y la piedra filosofal",
                "ja", "ハリー・ポッターと賢者の石",
                "ko", "해리 포터와 마법사의 돌",
                "it", "Harry Potter e la pietra filosofale",
                "de", "Harry Potter und der Stein der Weisen"
        ));
        LOCALIZABLE_MAP.put("2", book2);

        Map<String, Map<String, String>> book3 = new HashMap<>();
        book3.put("id", Map.of("value", "3"));
        book3.put("title", Map.of(
                "en", "One Hundred Years of Solitude",
                "fr", "Cent ans de solitude",
                "es", "Cien años de soledad",
                "ja", "百年の孤独",
                "ko", "백년의 고독",
                "it", "Cent'anni di solitudine",
                "de", "Hundert Jahre Einsamkeit"
        ));
        LOCALIZABLE_MAP.put("3", book3);

        Map<String, Map<String, String>> book4 = new HashMap<>();
        book4.put("id", Map.of("value", "4"));
        book4.put("title", Map.of(
                "en", "Pride and Prejudice",
                "fr", "Orgueil et Préjugés",
                "es", "Orgullo y prejuicio",
                "ja", "高慢と偏見",
                "ko", "오만과 편견",
                "it", "Orgoglio e pregiudizio",
                "de", "Stolz und Vorurteil"
        ));
        LOCALIZABLE_MAP.put("4", book4);

        Map<String, Map<String, String>> book5 = new HashMap<>();
        book5.put("id", Map.of("value", "5"));
        book5.put("title", Map.of(
                "en", "The Little Prince",
                "fr", "Le Petit Prince",
                "es", "El Principito",
                "ja", "星の王子さま",
                "ko", "어린 왕자",
                "it", "Il Piccolo Principe",
                "de", "Der kleine Prinz"
        ));
        LOCALIZABLE_MAP.put("5", book5);

        Map<String, Map<String, String>> book6 = new HashMap<>();
        book6.put("id", Map.of("value", "6"));
        book6.put("title", Map.of(
                "en", "Crime and Punishment",
                "fr", "Crime et Châtiment",
                "es", "Crimen y Castigo",
                "ja", "罪と罰",
                "ko", "죄와 벌",
                "it", "Delitto e castigo",
                "de", "Schuld und Sühne"
        ));
        LOCALIZABLE_MAP.put("6", book6);

        Map<String, Map<String, String>> book7 = new HashMap<>();
        book7.put("id", Map.of("value", "7"));
        book7.put("title", Map.of(
                "en", "The Diary of a Young Girl",
                "fr", "Le Journal d'Anne Frank",
                "es", "El Diario de Ana Frank",
                "ja", "アンネの日記",
                "ko", "안네의 일기",
                "it", "Il Diario di Anna Frank",
                "de", "Das Tagebuch der Anne Frank"
        ));
        LOCALIZABLE_MAP.put("7", book7);

        Map<String, Map<String, String>> book8 = new HashMap<>();
        book8.put("id", Map.of("value", "8"));
        book8.put("title", Map.of(
                "en", "Don Quixote",
                "fr", "Don Quichotte",
                "es", "Don Quijote de la Mancha",
                "ja", "ドン・キホーテ",
                "ko", "돈키호테",
                "it", "Don Chisciotte della Mancia",
                "de", "Don Quijote"
        ));
        LOCALIZABLE_MAP.put("8", book8);

        Map<String, Map<String, String>> book9 = new HashMap<>();
        book9.put("id", Map.of("value", "9"));
        book9.put("title", Map.of(
                "en", "The Bhagavad Gita",
                "fr", "La Bhagavad Gita",
                "es", "El Bhagavad Gita",
                "ja", "バガヴァッド・ギーター",
                "ko", "바가바드 기타",
                "it", "La Bhagavad Gita",
                "de", "Die Bhagavad Gita"
        ));
        LOCALIZABLE_MAP.put("9", book9);

        Map<String, Map<String, String>> book10 = new HashMap<>();
        book10.put("id", Map.of("value", "10"));
        book10.put("title", Map.of(
                "en", "Things Fall Apart",
                "fr", "Le Monde S'effondre",
                "es", "Todo se desmorona",
                "ja", "崩れゆく絆",
                "ko", "모든 것이 무너지다",
                "it", "Il crollo",
                "de", "Alles zerfällt"
        ));
        LOCALIZABLE_MAP.put("10", book10);
    }


    @Override
    public void serialize(Collection<E> localizables, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        log.info("Inside serialization");

        // Get the current locale
        Locale locale = LocaleContextHolder.getLocale();

        // Serialize the collection
        gen.writeStartArray();
        for (E localizable : localizables) {
            try {
                // Retrieve the object based on id
                String id = localizable.getId();
                Map<String, Map<String, String>> localizableData = LOCALIZABLE_MAP.get(id);

                if (localizableData != null) {
                    // Extract localized title based on the current locale
                    String title = localizableData.get("title").getOrDefault(locale.getLanguage(), "No title available");

                    // Set the title dynamically
                    localizable.setTitle(title);

                    // Use defaultSerialize to handle dynamic serialization
                    defaultSerialize(List.of(localizable), gen, serializers);
                } else {
                    log.warn("No localized data found for id: {}", id);
                }
            } catch (Exception e) {
                log.error("Error during serialization", e);
            }
        }
        gen.writeEndArray();
    }

    // Method to create a new instance of the Localizable object (E)
    private void defaultSerialize(Collection<E> localizables, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (E localizable : localizables) {
            try {
                gen.writeStartObject();
                JavaType javaType = serializers.constructType(localizable.getClass());
                BeanSerializerFactory beanSerializerFactory = BeanSerializerFactory.instance;
                BeanDescription beanDesc = serializers.getConfig().introspect(javaType);
                JsonSerializer<Object> serializer = beanSerializerFactory.findBeanSerializer(serializers, javaType, beanDesc);
                serializer.unwrappingSerializer(null).serialize(localizable, gen, serializers);
                gen.writeEndObject();
            } catch (IOException e) {
                throw new RuntimeException("Error serializing object", e);
            }
        }
        gen.writeEndArray();
    }
}

