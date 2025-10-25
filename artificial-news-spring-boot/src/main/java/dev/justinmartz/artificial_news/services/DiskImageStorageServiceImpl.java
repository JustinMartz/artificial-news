package dev.justinmartz.artificial_news.services;

import dev.justinmartz.artificial_news.exceptions.ArticleNotCreatedException;
import dev.justinmartz.artificial_news.models.ArticlePhotoDto;
import dev.justinmartz.artificial_news.models.Scale;
import java.awt.Graphics2D;
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

    private static final Scale<Integer, Integer> SCALE_FULLSIZE = new Scale<>(512, 512);
    private static final Scale<Integer, Integer> SCALE_THUMBNAIL = new Scale<>(256, 256);

    public DiskImageStorageServiceImpl() {}

    @Override
    public String saveAuthorPhoto(ImageResponse imageResponse, String author) {
        // Produces something like
        // Avery-Williams-1721680383905.png
        String filename =
                author.replaceAll("\\s+", "-") + "-" + System.currentTimeMillis() + ".png";

        saveResizedPhoto(imageResponse, filename, SCALE_FULLSIZE);

        return filename;
    }

    @Override
    public ArticlePhotoDto saveArticlePhoto(ImageResponse imageResponse, String headline) {
        // Produces something like
        // Undead-Fun-Zombie-Apocalypse-Marathon-Takes-Over-New-York-City-1721686055684.png
        ArticlePhotoDto articlePhotoDto = new ArticlePhotoDto();
        String fullsize =
                headline.replaceAll("[\\s,:]+", "-") + "-" + System.currentTimeMillis() + ".png";
        String thumbnail =
                headline.replaceAll("[\\s,:]+", "-")
                        + "-"
                        + System.currentTimeMillis()
                        + "-thumbnail"
                        + ".png";

        if (saveResizedPhoto(imageResponse, fullsize, SCALE_FULLSIZE)) {
            articlePhotoDto.setFullsize(fullsize);
        }

        if (saveResizedPhoto(imageResponse, thumbnail, SCALE_THUMBNAIL)) {
            articlePhotoDto.setThumbnail(thumbnail);
        }

        return articlePhotoDto;
    }

    private boolean saveResizedPhoto(
            ImageResponse imageResponse, String filename, Scale<Integer, Integer> scale) {
        byte[] imageBytes =
                Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());

        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);
            if (image == null) {
                throw new ArticleNotCreatedException("in saveResizedPhoto(): ", new IOException());
            }

            BufferedImage resizedImage =
                    new BufferedImage(scale.width(), scale.height(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = resizedImage.createGraphics();
            graphics2D.drawImage(image, 0, 0, scale.width(), scale.height(), null);
            graphics2D.dispose();

            File outputFile = new File(uploadDirectory, filename);
            ImageIO.write(resizedImage, "png", outputFile);

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
