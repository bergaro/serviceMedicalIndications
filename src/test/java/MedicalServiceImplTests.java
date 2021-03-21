import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;

public class MedicalServiceImplTests {

    private static HealthInfo healthInfo;
    private static PatientInfo patientInfo;
    private static SendAlertService sendAlertService;

    @BeforeAll
    public static void init() {
        sendAlertService = new SendAlertServiceImpl();
        healthInfo = new HealthInfo(new BigDecimal("36.6"), new BloodPressure(125, 78));
        patientInfo = new PatientInfo("Семен", "Михайлов",
                LocalDate.of(1982, 1, 16),
                healthInfo
        );
    }

    @Test
    public void checkBloodPressureTest() throws Exception {
        String expected = "Warning, patient with id: null, need help";
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("44e128a5-ac7a-4c9a-be4c-224b6bf81b20")).thenReturn(patientInfo);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        BloodPressure bloodPressure = new BloodPressure(60, 120);
        //Библиотека "System Lambda" умеет читать с консоли.
        String actual = tapSystemOut(() -> {
            medicalService.checkBloodPressure("44e128a5-ac7a-4c9a-be4c-224b6bf81b20", bloodPressure);
        });
        Assertions.assertEquals(expected, actual.trim());
    }

    @Test
    public void checkNormalBloodPressureTest() throws Exception {
        String expected = "";
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("44e128a5-ac7a-4c9a-be4c-224b6bf81b20")).thenReturn(patientInfo);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        BloodPressure bloodPressure = new BloodPressure(125, 78);
        //Библиотека "System Lambda" умеет читать с консоли.
        String actual = tapSystemOut(() -> {
            medicalService.checkBloodPressure("44e128a5-ac7a-4c9a-be4c-224b6bf81b20", bloodPressure);
        });
        Assertions.assertEquals(expected, actual.trim());
    }

    @Test
    public void checkTemperatureTest() throws Exception {
        String expected = "Warning, patient with id: null, need help";
        BigDecimal currentTemperature = new BigDecimal("20.4");
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("44e128a5-ac7a-4c9a-be4c-224b6bf81b20")).thenReturn(patientInfo);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        //Библиотека "System Lambda" умеет читать с консоли.
        String actual = tapSystemOut(() -> {
            medicalService.checkTemperature("44e128a5-ac7a-4c9a-be4c-224b6bf81b20", currentTemperature);
        });
        Assertions.assertEquals(expected, actual.trim());
    }

    @Test
    public void checkNormalTemperatureTest() throws Exception {
        String expected = "";
        BigDecimal currentTemperature = new BigDecimal("36.6");
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("44e128a5-ac7a-4c9a-be4c-224b6bf81b20")).thenReturn(patientInfo);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        //Библиотека "System Lambda" умеет читать с консоли.
        String actual = tapSystemOut(() -> {
            medicalService.checkTemperature("44e128a5-ac7a-4c9a-be4c-224b6bf81b20", currentTemperature);
        });
        Assertions.assertEquals(expected, actual.trim());
    }

}
