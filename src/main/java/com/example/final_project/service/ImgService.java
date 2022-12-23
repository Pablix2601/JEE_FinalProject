package com.example.final_project.service;

import com.example.final_project.entities.SurfBoards;
import com.example.final_project.entities.User;
import com.example.final_project.repositories.SurfBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service @AllArgsConstructor
public class ImgService {
    private final SurfBoardRepository surfBoardRepository;

    public static byte[] compressImage(byte[] data) {

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        System.out.println("test");
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
            System.out.println("test while");
        }
        try {
            outputStream.close();
        } catch (Exception e) {
            System.out.println("error");
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
        }
        return outputStream.toByteArray();
    }

    public static List<SurfBoards> stringImgEncoded(List<SurfBoards> listSb) {
        if (listSb != null) {
            SurfBoards sbTmp = new SurfBoards();
            for (int i = 0 ; i < listSb.size() ; i++) {
                sbTmp = listSb.get(i);
                if (listSb.get(i).getImage() != null) {
                    byte[] tmp = Base64.getEncoder().encode(ImgService.decompressImage(listSb.get(i).getImage()));
                    String base64Encoded = new String(tmp, StandardCharsets.UTF_8);
                    sbTmp.setImgEncoded(base64Encoded);
                    listSb.set(i,sbTmp);
                }
            }
        }
        return listSb;
    }

    public static List<User> stringAvatarEncoded(List<User> listUser) {
        if (listUser != null) {
            User userTmp = new User();
            for (int i = 0 ; i < listUser.size() ; i++) {
                userTmp = listUser.get(i);
                if (userTmp != null) {
                    if (listUser.get(i).getImage() == null) {
                        userTmp.setImgEncoded(listUser.get(i).getPrenom().charAt(0)
                                + "" + listUser.get(i).getNom().charAt(0));
                    } else {
                        byte[] tmp = Base64.getEncoder().encode(ImgService.decompressImage(listUser.get(i).getImage()));
                        String base64Encoded = new String(tmp, StandardCharsets.UTF_8);
                        userTmp.setImgEncoded(base64Encoded);
                    }
                    listUser.set(i,userTmp);
                }
            }
        }
        return listUser;
    }
}
