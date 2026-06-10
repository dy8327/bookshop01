package com.example.bookshop01.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.coobird.thumbnailator.Thumbnails;

@Controller
public class FileDownloadController {
    private static final String CURR_IMAGE_REPO_PATH = "C:\\shopping\\file_repo";

    @RequestMapping("/download")
    protected void download(@RequestParam("fileName") String fileName,
                            @RequestParam("goods_id") String goods_id,
                            HttpServletResponse response) throws Exception {

        String filePath = CURR_IMAGE_REPO_PATH + "\\" + goods_id + "\\" + fileName;
        File image = new File(filePath);

        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try (OutputStream out = response.getOutputStream();
             FileInputStream in = new FileInputStream(image)) {

            byte[] buffer = new byte[1024 * 8];
            int count;

            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
        }
    }

    @RequestMapping("/thumbnails.do")
    protected void thumbnails(@RequestParam("fileName") String fileName,
                              @RequestParam("goods_id") String goods_id,
                              HttpServletResponse response) throws Exception {

        String filePath = CURR_IMAGE_REPO_PATH + "\\" + goods_id + "\\" + fileName;
        File image = new File(filePath);

        if (image.exists()) {
            response.setContentType("image/png");

            try (OutputStream out = response.getOutputStream()) {
                Thumbnails.of(image)
                          .size(121, 154)
                          .outputFormat("png")
                          .toOutputStream(out);
            }
        }
    }
}