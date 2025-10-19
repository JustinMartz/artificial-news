package dev.justinmartz.artificial_news.services;

import dev.justinmartz.artificial_news.exceptions.ArticleNotCreatedException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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

        byte[] imageBytes =
                Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());

        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);
            if (image == null) {
                throw new ArticleNotCreatedException("saveAuthorPhoto()", new IOException());
            }

            File outputFile = new File(uploadDirectory, filename);
            ImageIO.write(image, "png", outputFile);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String saveArticlePhoto(ImageResponse imageResponse, String headline) {
        //     Produces something like
        //     Undead-Fun-Zombie-Apocalypse-Marathon-Takes-Over-New-York-City-1721686055684.png

        String filename =
                headline.replaceAll("[\\s,:]+", "-") + "-" + System.currentTimeMillis() + ".png";

        byte[] imageBytes =
                Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());

        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);
            if (image == null) {
                throw new ArticleNotCreatedException("saveArticlePhoto()", new IOException());
            }

            File outputFile = new File(uploadDirectory, filename);
            ImageIO.write(image, "png", outputFile);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
