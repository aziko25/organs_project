package organs.organs.Controllers.Photos;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

@Controller
@RequiredArgsConstructor
public class PhotosController {

    @GetMapping(value = "/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody Resource getImageName(@PathVariable String imageName) {

        File file = new File("/var/www/images/" + imageName);

        return new FileSystemResource(file);
    }
}