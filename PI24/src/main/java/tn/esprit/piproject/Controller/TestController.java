package tn.esprit.piproject.Controller;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.piproject.Entities.Complaint;
import tn.esprit.piproject.helpers.JavaMailSenderHelper;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "http://localhost:4200")
public class TestController {

    private final JavaMailSenderHelper mailSenderHelper;
    public TestController(JavaMailSenderHelper mailSenderHelper){
        this.mailSenderHelper=mailSenderHelper;
    }

    @GetMapping("/mailsender/{receiver}")
    public ResponseEntity<?> testMailSender(@PathVariable String receiver) throws  Exception{
        try {

            mailSenderHelper.sendEmail("SUBJECT TEST", "HELLO WORLD", receiver);
            return new ResponseEntity<>(Arrays.asList("Test Passed...", "Msg sent To : " + receiver), HttpStatus.OK);

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(Arrays.asList(e.getMessage(), e.getCause()), HttpStatus.BAD_REQUEST);
        }

    }



}
