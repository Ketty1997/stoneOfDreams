package com.example.demo.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.builder.UserDtoBuilder;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public void validateImageFile(UserDto userDto, BindingResult result) {
        /* --- In UserDto per il campo imageFile non abbiamo una validazione gia impostata come con gli altri parametri, ma
        * è importante che sia presente, quindi possiamo scriverla qui a mano:*/
        if (userDto.getImg().isEmpty()) {
            result.addError(new FieldError("userDto", "img", "the image file is required"));
        }
    }



    public String saveImage(MultipartFile image) throws IOException {

        Date createdAt = new Date(); /*createdAt.getTime() restituisce 1725088960724, Questo numero è semplicemente un conteggio dei millisecondi trascorsi dall'inizio dell'epoca UNIX (1 gennaio 1970). */
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
        String uploadDir = "public/images/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        /* Questo blocco di codice mostra un'implementazione del concetto di try-with-resources, una funzionalità introdotta in Java 7 che semplifica 
        la gestione delle risorse che devono essere chiuse, come file, stream o connessioni di rete. Esaminiamo ogni parte del codice:
        InputStream inputStream = image.getInputStream(): Questo esprime che stai aprendo una risorsa, in questo caso un InputStream ottenuto dall'oggetto image. 
        Il metodo image.getInputStream() restituisce un flusso di input che può essere utilizzato per leggere i dati contenuti in image.

        -inputStream: È il flusso di input da cui leggere i dati. In questo caso, i dati vengono letti dall'immagine rappresentata dall'oggetto image.
        -Paths.get(uploadDir + storageFileName): Questo costruisce il percorso completo dove il file verrà salvato. uploadDir è la directory in cui il file 
            verrà memorizzato, e storageFileName è il nome del file.
        -StandardCopyOption.REPLACE_EXISTING: Questa opzione specifica che, se un file esiste già nella destinazione con lo stesso nome, esso verrà sovrascritto.*/
        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
        }

        return storageFileName;
    }


    public void saveUser(UserDto userDto, String imageFileName, User user) {

        /* In una classe Builder (UserDtoBuilder.java in questo caso), i metodi statici trasformano direttamente il dto in utente*/
        User insertUser = UserDtoBuilder.UserFromDtoToEntity(userDto);

        userRepository.save(insertUser);
    }



    public User getUserFromSession(HttpSession session) {

        //prendo l'utente tramite l'id della sessione
        User user = userRepository.findById((Integer) session.getAttribute("user")).get();
        return user;
    }

    
}
