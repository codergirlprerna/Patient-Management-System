package com.pm.patientService.service;

import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.exception.EmailAlreadyExistsException;
import com.pm.patientService.exception.PatientNotFoundException;
import com.pm.patientService.mapper.PatientMapper;
import com.pm.patientService.model.Patient;
import com.pm.patientService.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository){
        this.patientRepository=patientRepository;
    }
    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients=patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toDTO).toList();
       }

       public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A Patient with this email" + "already exits" + patientRequestDTO.getEmail());
        }
        Patient newPatient=patientRepository.save(
        PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
       }
       public PatientResponseDTO updatePatient(UUID id,PatientRequestDTO patientRequestDTO){
        Patient patient=patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient not found with id:",id));
           if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
               throw new EmailAlreadyExistsException("A Patient with this email" + "already exits" + patientRequestDTO.getEmail());
           }
           patient.setName(patientRequestDTO.getName());
           patient.setAddress(patientRequestDTO.getAddress());
           patient.setEmail(patientRequestDTO.getEmail());
           patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

           Patient updatedPatient=patientRepository.save(patient);


       }
}
