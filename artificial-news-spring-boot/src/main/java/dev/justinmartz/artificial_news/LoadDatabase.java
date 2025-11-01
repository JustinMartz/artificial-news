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
        articleEntity1.setCreationTime(Long.parseLong("123456"));

        articleEntity2.setCreatedAt(OffsetDateTime.parse("2025-10-11T08:38:39.969026Z"));
        articleEntity2.setDateline("October 11, 2025 • 8:38 AM");
        articleEntity2.setHeadline("Ghostly Delights Await at Denver's Haunted Cupcake Festival");
        articleEntity2.setAuthor("Samantha Lin");
        articleEntity2.setAuthorPhoto("Samantha-Lin-1749393518091.png");
        articleEntity2.setArticleBody(
                "This October, Denver will play host to a spine-tingling affair that promises to be"
                    + " as sweet as it is spectral: the Haunted Cupcake Festival. Set against the"
                    + " backdrop of the city's historic Larimer Square, the festival will kick off"
                    + " on October 14th, just in time to get everyone into the Halloween spirit."
                    + " Organized by the local baking enthusiasts of the Denver Sweet Society, the"
                    + " event invites both locals and tourists to indulge in a delectable array of"
                    + " cupcakes while experiencing a few spooky thrills along the way.\n\n"
                    + "Among the many attractions, the festival will feature a haunted maze crafted"
                    + " entirely out of cupcake-themed decor, providing a unique twist on the"
                    + " traditional haunted house experience. \"We wanted to create something fun"
                    + " and whimsical that would appeal to all ages,\" said Marissa Caldwell, event"
                    + " coordinator and founder of the Denver Sweet Society. \"Our cupcakes will be"
                    + " dressed up in their Halloween best, from ghoulish ghosts to decadent dark"
                    + " chocolate bats. \"Visitors can expect an impressive variety of flavors,"
                    + " including caramel apple cobweb, black velvet, and even pumpkin spice"
                    + " specter.\n\n"
                    + "The festival hopes to draw a large crowd thanks to its combination of"
                    + " culinary creativity and festive fun. All proceeds from the event will"
                    + " benefit the Children’s Hospital Colorado, ensuring that the kitchens of"
                    + " Denver's bakers will not only whip up delightful desserts, but also serve a"
                    + " good cause. \"It's incredible to see how food can bring people together for"
                    + " such a meaningful purpose,\" Caldwell added. As the festival date"
                    + " approaches, anticipation is building for what promises to be an"
                    + " unforgettable celebration of both the spooky and the sweet.");
        articleEntity2.setArticlePhoto(testPhotos.get(1));

        articleEntity3.setCreatedAt(OffsetDateTime.parse("2025-10-10T08:43:26.153504Z"));
        articleEntity3.setDateline("October 10, 2025 • 8:43 AM");
        articleEntity3.setHeadline("Mystical Quilts Draw Bidders to Savannah's Eerie Auction");
        articleEntity3.setAuthor("Lila Ellis");
        articleEntity3.setAuthorPhoto("Lila-Ellis-1749393805761.png");
        articleEntity3.setArticleBody(
                "In the heart of Savannah, Georgia, where history intertwines with mysticism, an"
                    + " unusual auction drew crowds to the city’s historic district this past"
                    + " weekend. The Ghostly Quilt Auction, hosted at the renowned Davenport House"
                    + " Museum, featured an array of handcrafted quilts said to be imbued with"
                    + " ethereal qualities. Each quilt carries a touch of Savannah's storied past,"
                    + " reportedly stitched by hands long since gone, and according to local lore,"
                    + " under the supervision of benevolent spirits. The event held on the eve of"
                    + " Halloween added an extra layer of intrigue, inviting both skeptics and"
                    + " believers alike to bid on these textile pieces of history.\n\n"
                    + "The quilts, painstakingly curated by local historian Margaret Jenkins, are"
                    + " believed to possess supernatural attributes, with patterns that"
                    + " occasionally change ever so subtly under moonlight. \"These quilts are more"
                    + " than just fabric and thread,\" Jenkins explained to attendees, her voice"
                    + " echoing through the halls once walked by figures of the 19th century."
                    + " \"They tell the stories of the past, whispers sewn into each stitch.\" The"
                    + " auction attracted collectors nationwide who sought to own not just a piece"
                    + " of art, but a fragment of an otherworldly narrative that ties them to the"
                    + " city's vibrant and often mysterious history.\n\n"
                    + "Despite the spectral claims, the event served as a boon for Savannah’s"
                    + " tourism, drawing attendees from all over the U.S. while spotlighting the"
                    + " city's rich tapestry of folklore. John Michaels, a noted folklorist at the"
                    + " University of Georgia, expressed admiration for how the event highlighted"
                    + " Savannah’s unique blend of history and mystery. \"This auction demonstrates"
                    + " the enduring allure of Savannah's past,\" Michaels noted. \"It's a city"
                    + " that never forgets its ghosts and celebrates the stories they have left"
                    + " behind.\" With all proceeds going to the preservation of local historical"
                    + " sites, the Ghostly Quilt Auction not only captivated imaginations but also"
                    + " contributed to the safeguarding of Savannah's heritage.");
        articleEntity3.setArticlePhoto(testPhotos.get(2));

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
                                "Ghostly-Delights-Await-at-Denver's-Haunted-Cupcake-Festival-1749393519798.png")
                        .setCaption("Ghost and cupcakes haunt a festival in Denver.")
                        .setPhotographer("Taylor Bugatta");

        ArticlePhotoEntity articlePhotoEntity3 =
                new ArticlePhotoEntity()
                        .setFullsize(
                                "Mystical-Quilts-Draw-Bidders-to-Savannah's-Eerie-Auction-1749393804690.png")
                        .setCaption(
                                "An auction house display mystical quilts and potential bidders.")
                        .setPhotographer("Joan Malone");

        return List.of(articlePhotoEntity1, articlePhotoEntity2, articlePhotoEntity3);
    }
}
