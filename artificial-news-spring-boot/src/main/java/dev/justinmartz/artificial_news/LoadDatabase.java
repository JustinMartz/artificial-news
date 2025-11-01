package dev.justinmartz.artificial_news;

import dev.justinmartz.artificial_news.entities.ArticleEntity;
import dev.justinmartz.artificial_news.entities.ArticlePhotoEntity;
import dev.justinmartz.artificial_news.repositories.ArticlePhotoRepository;
import dev.justinmartz.artificial_news.repositories.ArticleRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "test-data.enabled", havingValue = "true")
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(
            ArticleRepository articleRepository, ArticlePhotoRepository articlePhotoRepository) {
        List<ArticlePhotoEntity> testPhotos = buildTestPhotos();
        List<ArticleEntity> testArticleEntities = buildTestArticles(testPhotos);

        articleRepository.saveAll(testArticleEntities);

        return args -> {
            for (ArticleEntity articleEntity : testArticleEntities) {
                log.info("Preloading: {}", articleEntity.getId());
            }
        };
    }

    private List<ArticleEntity> buildTestArticles(List<ArticlePhotoEntity> testPhotos) {
        ArticleEntity articleEntity1 = new ArticleEntity(),
                articleEntity2 = new ArticleEntity(),
                articleEntity3 = new ArticleEntity();

        articleEntity1.setCreatedAt(OffsetDateTime.parse("2025-11-01T08:16:54.519613Z"));
        articleEntity1.setDateline("November 1, 2025 • 4:45 PM");
        articleEntity1.setHeadline(
                "Diving into the Rhythm: Miami's Unique Underwater Jazz Festival");
        articleEntity1.setAuthor("Alicia Martinez");
        articleEntity1.setAuthorPhoto("Alicia-Martinez-1762037109267.png");
        articleEntity1.setArticleBody(
                "In an audacious blend of music and marine life, Miami hosted its first-ever"
                    + " Underwater Jazz Festival, bringing new meaning to the term 'immersive"
                    + " experience.' Held at the Miami Seaquarium, the event took place this past"
                    + " weekend and featured a collection of renowned jazz musicians performing"
                    + " beneath the waves. Concertgoers donned snorkeling gear and descended into"
                    + " the aquamarine depths, where they enjoyed the sultry sounds of saxophones"
                    + " and trumpets while surrounded by colorful schools of fish. \"It's unlike"
                    + " anything I've ever experienced,\" said festival attendee and jazz"
                    + " enthusiast Carlos Rivera. \"The music, the setting, it was absolutely"
                    + " magical.\"\n\n"
                    + "This groundbreaking event was the brainchild of local event organizer,"
                    + " Jasmine Lee, who sought to create a festival that would highlight Miami's"
                    + " unique position as a city between the sea and vibrant cultural artistry."
                    + " \"I wanted to create an event that not only celebrates the incredible music"
                    + " scene we have here but also our deep connection to the ocean,\" Lee"
                    + " explained. Musicians played from waterproof stages encased in glass, a"
                    + " spectacle that amazed the 500 attendees, many of whom were visiting Miami"
                    + " specifically for the festival.\n\n"
                    + "The festival not only promoted the jazz scene but also raised awareness for"
                    + " marine conservation efforts. Partnering with the Marine Conservation"
                    + " Institute, the event underscored the importance of preserving Miami's"
                    + " natural underwater habitats. \"We're thrilled to be part of something"
                    + " that's both entertaining and meaningful,\" said Sarah Thompson, head of the"
                    + " organization. \"Combining jazz with the ocean environment reminds us how"
                    + " precious our marine ecosystems are and how they deserve our collective"
                    + " efforts to protect them.\" With plans for future festivals already"
                    + " underway, the Underwater Jazz Festival is set to become a celebrated"
                    + " fixture of Miami's cultural calendar, merging music with messages of"
                    + " ecological stewardship.");
        articleEntity1.setArticlePhoto(testPhotos.get(0));
        articleEntity1.setProvider("OpenAI");
        articleEntity1.setModel("gpt-4o");
        articleEntity1.setCreationTime(Long.parseLong("24951"));

        articleEntity2.setCreatedAt(OffsetDateTime.parse("2025-11-02T08:38:39.969026Z"));
        articleEntity2.setDateline("November 2, 2025 • 5:00 PM");
        articleEntity2.setHeadline("The Mysterious Allure of Orlando’s Haunted Swings");
        articleEntity2.setAuthor("Jasmine Lee");
        articleEntity2.setAuthorPhoto("Jasmine-Lee-1762038010879.png");
        articleEntity2.setArticleBody(
                "In the heart of the vibrant city of Orlando, a unique phenomenon has been drawing"
                    + " curious thrill-seekers and paranormal enthusiasts alike: the haunted"
                    + " swings. These seemingly ordinary playground fixtures have become an enigma,"
                    + " captivating residents and visitors with tales of their supernatural"
                    + " activity. It all started in the suburb of Lake Eola Heights, where locals"
                    + " reported the swings moving on their own, even in the absence of any wind, a"
                    + " curiosity that turned into a legend.\n"
                    + "\n"
                    + "Michelle Thompson, a mother of two who frequents the playground, recounted"
                    + " her eerie experience. \"It was a quiet afternoon, and my kids were"
                    + " playing nearby when I noticed the swings just started moving by themselves."
                    + " I thought someone had given them a push, but no one was around,\" she"
                    + " said, her voice tinged with both excitement and unease. This playground has"
                    + " since become a hotspot for ghost hunters and tourists hoping to witness the"
                    + " swings in action.\n"
                    + "\n"
                    + "Experts offer differing explanations for the haunted swings of Orlando. Dr."
                    + " Alan Rodriguez, a physicist at the University of Central Florida, suggests"
                    + " that microclimates and unique wind currents could be responsible. However,"
                    + " local historian Amanda Green passionately argues that the swings' peculiar"
                    + " behavior stems from the area's rich history and the spirits of children who"
                    + " once played there. Whether the phenomenon is a result of science or the"
                    + " supernatural, the haunted swings have undeniably become a charmingly spooky"
                    + " staple in Orlando’s cultural tapestry.");
        articleEntity2.setArticlePhoto(testPhotos.get(1));
        articleEntity2.setProvider("OpenAI");
        articleEntity2.setModel("gpt-4o");
        articleEntity2.setCreationTime(Long.parseLong("25957"));

        articleEntity3.setCreatedAt(OffsetDateTime.parse("2025-11-03T08:43:26.153504Z"));
        articleEntity3.setDateline("November 3, 2025 • 5:15 PM");
        articleEntity3.setHeadline(
                "Feathers Fly and Feet Groove at Detroit's Disco Pigeon Festival");
        articleEntity3.setAuthor("Jamie Rivera");
        articleEntity3.setAuthorPhoto("Jamie-Rivera-1762038932758.png");
        articleEntity3.setArticleBody(
                "In an exuberant celebration of music, dance, and feathered flair, the annual Disco"
                    + " Pigeon Festival swooped into Detroit this past weekend, drawing crowds from"
                    + " across the Midwest. Held in Hart Plaza, the event, now in its fifth year,"
                    + " featured a plethora of DJs spinning classic disco tracks, while attendees"
                    + " dressed in sparkly costumes mingled with live pigeons, all adorned in hues"
                    + " reminiscent of the grooviest decade. The festival aimed to fuse the city's"
                    + " rich musical heritage with an unusual appreciation for its urban avian"
                    + " residents. \n\n"
                    + "\"We wanted to create a unique experience that captures the essence of"
                    + " Detroit's music scene while also highlighting the often-overlooked beauty"
                    + " of pigeons,\" explained festival founder and organizer, Benji Williams."
                    + " Attendees were treated to impressive dance performances, interactive avian"
                    + " arts and crafts booths, and even a pigeon parade, where birds strutted"
                    + " their stuff to the beats of their own soundscape. The spectacle was not"
                    + " just about entertainment; it served to educate the public on the ecological"
                    + " importance of pigeons and their role in urban environments.\n\n"
                    + "The highlight of the festival was undoubtedly the evening's grand finale, a"
                    + " synchronized light show and dance-off dubbed the \"Pigeon Boogie.\" As"
                    + " flocks of pigeons took flight, their feathers catching the multicolored"
                    + " lights, the crowd beneath them danced in jubilant unity. \"It's an"
                    + " extraordinary scene,\" said Laura Chen, a festival-goer and self-proclaimed"
                    + " pigeon enthusiast. \"Only in Detroit can you find such a wonderfully weird"
                    + " and rewarding blend of community, culture, and creativity.\" Plans for next"
                    + " year's festival are already underway, promising even more avian-themed"
                    + " surprises for all to enjoy.");
        articleEntity3.setArticlePhoto(testPhotos.get(2));
        articleEntity3.setProvider("OpenAI");
        articleEntity3.setModel("gpt-4o");
        articleEntity3.setCreationTime(Long.parseLong("24020"));

        return List.of(articleEntity1, articleEntity2, articleEntity3);
    }

    private List<ArticlePhotoEntity> buildTestPhotos() {
        ArticlePhotoEntity articlePhotoEntity1 =
                new ArticlePhotoEntity()
                        .setFullsize(
                                "Diving-into-the-Rhythm-Miami's-Unique-Underwater-Jazz-Festival-1762037109422.png")
                        .setThumbnail(
                                "Diving-into-the-Rhythm-Miami's-Unique-Underwater-Jazz-Festival-1762037109422-thumbnail.png")
                        .setCaption(
                                "Jazz musicians perform underwater at Miami Seaquarium, captivating"
                                        + " audiences with a unique musical experience.")
                        .setPhotographer("Derek Chau");

        ArticlePhotoEntity articlePhotoEntity2 =
                new ArticlePhotoEntity()
                        .setFullsize(
                                "The-Mysterious-Allure-of-Orlando’s-Haunted-Swings-1762038011011.png")
                        .setThumbnail(
                                "The-Mysterious-Allure-of-Orlando’s-Haunted-Swings-1762038011011-thumbnail.png")
                        .setCaption(
                                "The haunted swings in Lake Eola Heights sway under the mysterious"
                                        + " Orlando skies.")
                        .setPhotographer("Lucas Fernandez");

        ArticlePhotoEntity articlePhotoEntity3 =
                new ArticlePhotoEntity()
                        .setFullsize(
                                "Feathers-Fly-and-Feet-Groove-at-Detroit's-Disco-Pigeon-Festival-1762038932894.png")
                        .setThumbnail(
                                "Feathers-Fly-and-Feet-Groove-at-Detroit's-Disco-Pigeon-Festival-1762038932894-thumbnail.png")
                        .setCaption(
                                "Festival-goers dance beneath a dazzling pigeon-themed light show"
                                        + " at the Disco Pigeon Festival in Detroit.")
                        .setPhotographer("Samantha Kim");

        return List.of(articlePhotoEntity1, articlePhotoEntity2, articlePhotoEntity3);
    }
}
