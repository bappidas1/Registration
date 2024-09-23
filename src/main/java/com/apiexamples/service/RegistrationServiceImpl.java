package com.apiexamples.service;

import com.apiexamples.entity.Registration;
import com.apiexamples.exception.ResourceNotFound;
import com.apiexamples.payload.RegDto;
import com.apiexamples.payload.RegistrationDTO;
import com.apiexamples.repository.RegistrationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService{

    private RegistrationRepository registrationRepository;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }


    @Override
    public RegistrationDTO createRegistration(RegistrationDTO registrationDTO) {
        Registration registration = mapToEntity(registrationDTO);
        Registration savedEntity = registrationRepository.save(registration);
        RegistrationDTO dto = mapToDTO(savedEntity);
        dto.setMessage("registration saved");
        return dto;
    }

    @Override
    public void deleteRegistrationById(long id) {
        registrationRepository.deleteById(id);
    }

//    @Override
//    public List<Registration> getAllRegistration() {
//       return registrationRepository.findAll();
//    }

    @Override
    public RegistrationDTO updateRegistration(long id, RegistrationDTO registrationDTO) {
        Optional<Registration> opReg = registrationRepository.findById(id);
        Registration registration = opReg.get();
        registration.setName(registrationDTO.getName());
        registration.setEmail(registrationDTO.getEmail());
        registration.setMobile(registrationDTO.getMobile());
        Registration savedEntity = registrationRepository.save(registration);
        RegistrationDTO dto = mapToDTO(registration);

        return dto;



    }

    @Override
    public RegDto<RegistrationDTO> getAllRegistration(int pageNo, int pageSize, String sortBy, String sortDir) {


        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Registration> all = registrationRepository.findAll(pageable);
        List<Registration> registrations = all.getContent();

        List<RegistrationDTO> collect = registrations.stream().map(r -> mapToDTO(r)).collect(Collectors.toList());
        RegDto<RegistrationDTO> dtos = new RegDto<>();
        dtos.setDto(collect);
        dtos.setPageNo(all.getTotalPages());
        dtos.setPageSize(all.getSize());
        dtos.setTotalPages(all.getTotalPages());
        dtos.setLast(all.isLast());
        dtos.setFirst(all.isFirst());
        return dtos;
    }

    @Override
    public RegistrationDTO getRegistrationById(long id) {
        Registration registration = registrationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Registration not found with id:" + id)
        );

        RegistrationDTO dto = mapToDTO(registration);
        return dto;
    }

    Registration mapToEntity(RegistrationDTO dto){
        Registration entity = new Registration();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setMobile(dto.getMobile());

        return entity;
    }

    RegistrationDTO mapToDTO(Registration registration){
        RegistrationDTO dto = new RegistrationDTO();
        dto.setId(registration.getId());
        dto.setName(registration.getName());
        dto.setEmail(registration.getEmail());
        dto.setMobile(registration.getMobile());
        return dto;
    }

}
