package com.pm.patientService.service;

import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.mapper.PatientMapper;
import com.pm.patientService.model.Patient;
import com.pm.patientService.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Patient newPatient=patientRepository.save(
        PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
       }
}
