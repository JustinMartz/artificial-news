package dev.justinmartz.artificial_news.services;

import dev.justinmartz.artificial_news.entities.ArticlePhoto;
import dev.justinmartz.artificial_news.exceptions.ArticleNotCreatedException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DiskImageStorageServiceImpl implements ImageStorageService {
    @Value("${image.upload.dir}")
    private String uploadDirectory;

    public DiskImageStorageServiceImpl() {}

    @Override
    public String saveAuthorPhoto(ImageResponse imageResponse, String author) {
        // Produces something like Avery-Williams-1721680383905.png
        String filename =
                author.replaceAll("\\s+", "-") + "-" + System.currentTimeMillis() + ".png";

        String url = imageResponse.getResult().getOutput().getUrl();
        URL imageUrl = null;
        try {
            imageUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ImageIO.write(image, "png", new File(uploadDirectory, filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filename;
    }

    @Override
    public String saveArticlePhoto(ImageResponse imageResponse, String headline) {
        //     Produces something like
        //     Undead-Fun-Zombie-Apocalypse-Marathon-Takes-Over-New-York-City-1721686055684.png

        ArticlePhoto articlePhoto = new ArticlePhoto();
        String filename =
                headline.replaceAll("[\\s,:]+", "-") + "-" + System.currentTimeMillis() + ".png";

        byte[] imageBytes =
                Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());

        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);
            if (image == null) {
                throw new ArticleNotCreatedException(
                        "fetchAndSaveArticlePhoto()", new IOException());
            }

            File outputFile = new File(uploadDirectory, filename);
            ImageIO.write(image, "png", outputFile);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
