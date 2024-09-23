package com.apiexamples.service;

import com.apiexamples.payload.RegDto;







import com.apiexamples.payload.RegistrationDTO;

import java.util.List;

public interface RegistrationService {
    public RegistrationDTO createRegistration(RegistrationDTO registrationDTO);

    public void deleteRegistrationById(long id);

//    List<Registration> getAllRegistration();

    RegistrationDTO updateRegistration(long id, RegistrationDTO registrationDTO);

    RegDto<RegistrationDTO> getAllRegistration(int pageNo, int pageSize, String sortBy, String sortDir);

    RegistrationDTO getRegistrationById(long id);
}
