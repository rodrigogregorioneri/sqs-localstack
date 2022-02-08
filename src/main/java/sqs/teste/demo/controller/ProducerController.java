package sqs.teste.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sqs.teste.demo.config.ProducerService;
import sqs.teste.demo.model.Message;

@RestController
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @RequestMapping(value = "/queue", method = RequestMethod.POST)
    public ResponseEntity<String> imc(@RequestBody Message message) {
        producerService.send(message.getQueue(), String.valueOf(message.getTexto()));
        return new ResponseEntity<>("Enviado", HttpStatus.OK);
    }

}
