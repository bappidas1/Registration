package com.apiexamples.controller;

import com.apiexamples.payload.RegDto;
import com.apiexamples.payload.RegistrationDTO;
import com.apiexamples.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    //http://localhost:8081/api/v1/registration

    @PostMapping
    public ResponseEntity<?> addRegistration(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.OK);
        }
        RegistrationDTO regDto = registrationService.createRegistration(registrationDTO);
        return new ResponseEntity<>(regDto, HttpStatus.CREATED);
    }

    //http://localhost:8081/api/v1/registration?id=
    @DeleteMapping
    public ResponseEntity<String> deleteRegistrationById(@RequestParam long id){
        registrationService.deleteRegistrationById(id);
        return new ResponseEntity<>("Registration Deleted", HttpStatus.OK);

    }

//    @GetMapping
//    public List<Registration> getAllRegistration(){
//        return registrationService.getAllRegistration();
//    }

    @PutMapping
    public ResponseEntity<?> updateRegistration(
            @RequestParam long id,
            @Valid @RequestBody RegistrationDTO registrationDTO,
            BindingResult result
    ){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.OK);
        }
        RegistrationDTO regDto = registrationService.updateRegistration(id ,registrationDTO);
        return new ResponseEntity<>(regDto, HttpStatus.OK);

    }
    //http://localhost:8081/api/v1/registration?pageNo=1&pageSize=3&sortBy=email&sortDir=asc

    @GetMapping
    public ResponseEntity<RegDto<RegistrationDTO>> getAllRegistration(
            @RequestParam(name="pageNo", defaultValue = "0", required = false)int pageNo,
            @RequestParam(name="pageSize", defaultValue = "5", required = false)int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "name", required = false)String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "name", required = false)String sortDir
            ){
        RegDto<RegistrationDTO> dtos = registrationService.getAllRegistration(pageNo, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/byId")
    public ResponseEntity<RegistrationDTO> getRegistrationById(@RequestParam long id){
        RegistrationDTO dto = registrationService.getRegistrationById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
