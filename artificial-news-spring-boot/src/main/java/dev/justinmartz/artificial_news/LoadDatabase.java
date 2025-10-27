package dev.justinmartz.artificial_news;

import dev.justinmartz.artificial_news.entities.Article;
import dev.justinmartz.artificial_news.entities.ArticlePhoto;
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
        List<ArticlePhoto> testPhotos = buildTestPhotos();
        List<Article> testArticles = buildTestArticles(testPhotos);

        articleRepository.saveAll(testArticles);

        return args -> {
            for (Article article : testArticles) {
                log.info("Preloading: {}", article.getId());
            }
        };
    }

    private List<Article> buildTestArticles(List<ArticlePhoto> testPhotos) {
        Article article1 = new Article(), article2 = new Article(), article3 = new Article();

        article1.setCreatedAt(OffsetDateTime.parse("2025-10-12T08:16:54.519613Z"));
        article1.setDateline("October 12, 2025 • 8:16 AM");
        article1.setHeadline(
                "Giraffes Take to the Streets in Seattle's Most Whimsical Marathon Yet");
        article1.setAuthor("Naomi Fernandez");
        article1.setAuthorPhoto("Naomi-Fernandez-1749392212194.png");
        article1.setArticleBody(
                "In a delightful twist on the traditional marathon, the streets of Seattle played"
                    + " host to the first-ever Whimsical Giraffe Marathon over the weekend."
                    + " Participants, dressed in eye-catching giraffe costumes complete with long"
                    + " necks and spotted ensembles, ran through the city's most iconic routes,"
                    + " attracting cheering crowds and curious onlookers. The event, which took"
                    + " place on Saturday, aimed to combine fitness with fun while raising"
                    + " awareness for wildlife conservation efforts. \"It's not just about running;"
                    + " it's about making a statement and bringing joy,\" said event organizer Lisa"
                    + " Thompson.\n\n"
                    + "The marathon began at the crack of dawn at the famous Seattle Center,"
                    + " weaving through the bustling Pike Place Market, and culminating at the"
                    + " serene shores of Green Lake. Competitors, ranging from serious athletes to"
                    + " families with young children, embraced the whimsy of the day with gusto."
                    + " The race was not timed, encouraging participants to enjoy the journey"
                    + " rather than rush to the finish line. \"I came with my kids, and they"
                    + " absolutely loved it,\" shared local resident, Mark Johnson, who"
                    + " participated with his family. \"It's a fantastic way to engage the"
                    + " community and support a good cause.\"\n\n"
                    + "Beyond the entertainment value, the Whimsical Giraffe Marathon served a"
                    + " deeper purpose. A portion of the proceeds was directed towards giraffe"
                    + " conservation projects in Africa, underscoring the plight of these gentle"
                    + " giants whose populations have been dwindling due to habitat loss and"
                    + " poaching. The event highlighted the importance of international cooperation"
                    + " in wildlife preservation and drew attention to Seattle's commitment to"
                    + " global conservation efforts. As the day concluded with a festive atmosphere"
                    + " at the finish line, complete with music and refreshments, Thompson was"
                    + " optimistic about growing the event in years to come. \"This is just the"
                    + " beginning of a new tradition in Seattle,\" she exclaimed, her eyes gleaming"
                    + " with hope.");
        article1.setArticlePhoto(testPhotos.get(0));

        article2.setCreatedAt(OffsetDateTime.parse("2025-10-11T08:38:39.969026Z"));
        article2.setDateline("October 11, 2025 • 8:38 AM");
        article2.setHeadline("Ghostly Delights Await at Denver's Haunted Cupcake Festival");
        article2.setAuthor("Samantha Lin");
        article2.setAuthorPhoto("Samantha-Lin-1749393518091.png");
        article2.setArticleBody(
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
        article2.setArticlePhoto(testPhotos.get(1));

        article3.setCreatedAt(OffsetDateTime.parse("2025-10-10T08:43:26.153504Z"));
        article3.setDateline("October 10, 2025 • 8:43 AM");
        article3.setHeadline("Mystical Quilts Draw Bidders to Savannah's Eerie Auction");
        article3.setAuthor("Lila Ellis");
        article3.setAuthorPhoto("Lila-Ellis-1749393805761.png");
        article3.setArticleBody(
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
        article3.setArticlePhoto(testPhotos.get(2));

        return List.of(article1, article2, article3);
    }

    private List<ArticlePhoto> buildTestPhotos() {
        ArticlePhoto articlePhoto1 =
                new ArticlePhoto()
                        .setFullsize(
                                "Giraffes-Take-to-the-Streets-in-Seattle's-Most-Whimsical-Marathon-Yet-1749392214339.png")
                        .setCaption("Giraffes running around Seattle all crazy in a marathon.")
                        .setPhotographer("John Smith");

        ArticlePhoto articlePhoto2 =
                new ArticlePhoto()
                        .setFullsize(
                                "Ghostly-Delights-Await-at-Denver's-Haunted-Cupcake-Festival-1749393519798.png")
                        .setCaption("Ghost and cupcakes haunt a festival in Denver.")
                        .setPhotographer("Taylor Bugatta");

        ArticlePhoto articlePhoto3 =
                new ArticlePhoto()
                        .setFullsize(
                                "Mystical-Quilts-Draw-Bidders-to-Savannah's-Eerie-Auction-1749393804690.png")
                        .setCaption(
                                "An auction house display mystical quilts and potential bidders.")
                        .setPhotographer("Joan Malone");

        return List.of(articlePhoto1, articlePhoto2, articlePhoto3);
    }
}
