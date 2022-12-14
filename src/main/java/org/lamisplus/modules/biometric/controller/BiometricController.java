package org.lamisplus.modules.biometric.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.biometric.domain.Biometric;
import org.lamisplus.modules.biometric.domain.BiometricDevice;
import org.lamisplus.modules.biometric.domain.dto.BiometricDto;
import org.lamisplus.modules.biometric.domain.dto.BiometricEnrollmentDto;
import org.lamisplus.modules.biometric.domain.dto.CapturedBiometricDTOS;
import org.lamisplus.modules.biometric.repository.BiometricDeviceRepository;
import org.lamisplus.modules.biometric.services.BiometricService;
import org.lamisplus.modules.biometric.services.SecugenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BiometricController {
    private final BiometricService biometricService;
    private final BiometricDeviceRepository biometricDeviceRepository;
    private final SecugenService secugenService;
    //Versioning through URI Path
    private final String BASE_URL_VERSION_ONE = "/api/v1/biometrics";
    @PostMapping(BASE_URL_VERSION_ONE + "/templates")
    public ResponseEntity<BiometricDto> saveBiometric(@RequestBody BiometricEnrollmentDto biometrics) {
        return ResponseEntity.ok (biometricService.biometricEnrollment (biometrics));
    }
    @GetMapping(BASE_URL_VERSION_ONE + "/patient/{id}")
    public ResponseEntity<CapturedBiometricDTOS> findByPatient(@PathVariable Long id) {
        return ResponseEntity.ok (biometricService.getByPersonId (id));
    }
    @PostMapping(BASE_URL_VERSION_ONE + "/device")
    public ResponseEntity<BiometricDevice> saveBiometric(@RequestBody BiometricDevice biometricDevice) {
        return ResponseEntity.ok (biometricDeviceRepository.save (biometricDevice));
    }
    @PutMapping(BASE_URL_VERSION_ONE + "/device/{id}")
    public ResponseEntity<BiometricDevice> update(@PathVariable Long id, @RequestBody BiometricDevice biometricDevice) {
        return ResponseEntity.ok (biometricService.update (id, biometricDevice));
    }
    @PutMapping(BASE_URL_VERSION_ONE + "/person/{personId}")
    public ResponseEntity<BiometricDto> updatePersonBiometric(@PathVariable Long personId, @RequestBody BiometricEnrollmentDto biometricEnrollmentDto) {
        return ResponseEntity.ok (biometricService.updatePersonBiometric (personId, biometricEnrollmentDto));
    }
    @DeleteMapping(BASE_URL_VERSION_ONE + "/device/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        biometricService.delete (id);
    }
    @GetMapping(BASE_URL_VERSION_ONE + "/devices")
    public ResponseEntity<List<BiometricDevice>> getAllBiometricDevice(@RequestParam (required = false,
            defaultValue = "false") boolean active) {
        return ResponseEntity.ok (biometricService.getAllBiometricDevices(active));
    }
    @GetMapping(BASE_URL_VERSION_ONE + "/person/{personId}")
    public ResponseEntity<List<Biometric>> getAllPersonBiometric(@PathVariable Long personId) {
        return ResponseEntity.ok (biometricService.getAllPersonBiometric(personId));
    }
    @PostMapping(BASE_URL_VERSION_ONE + "/store-list/{personId}")
    public ResponseEntity<Boolean> clearStoreList(@PathVariable Long personId) {
        return ResponseEntity.ok (secugenService.emptyStoreByPersonId(personId));
    }
    @DeleteMapping(BASE_URL_VERSION_ONE + "/person/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllPersonBiometrics(@PathVariable Long personId) {
        biometricService.deleteAllPersonBiometrics (personId);
    }
    @DeleteMapping(BASE_URL_VERSION_ONE + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBiometrics(@PathVariable String id) {
        biometricService.deleteBiometrics (id);
    }
}
