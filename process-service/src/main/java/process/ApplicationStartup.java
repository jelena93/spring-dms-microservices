package process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import process.domain.Activity;
import process.domain.Process;
import process.service.ProcessService;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${add-to-db}")
    private boolean addToDb;

    @Autowired
    ProcessService processService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        if (addToDb) {
            Process prodaja = new Process("Prodaja", null, false);
            Process nabavka = new Process("Nabavka", null, false);
            Process skladistenje = new Process("Skladistenje i oprema", null, false);
            Process finansije = new Process("Regulisanje finansija", null, false);

            prodaja = processService.save(prodaja);
            nabavka = processService.save(nabavka);
            skladistenje = processService.save(skladistenje);
            finansije = processService.save(finansije);

            Process katalog = new Process("Formiranje i slanje kataloga", prodaja, true);
            katalog.getActivityList().add(new Activity("Formiranje kataloga"));
            katalog.getActivityList().add(new Activity("Slanje kataloga"));

            Process narudzbenica = new Process("Prijem narudzbenice", prodaja, false);
            Process otprema = new Process("Formiranje naloga za otpremu", prodaja, false);

            Process profaktura = new Process("Formiranje profakture", prodaja, true);
            profaktura.getActivityList().add(new Activity("Provera raspolozivosti"));
            profaktura.getActivityList().add(new Activity("Formiranje profakture"));
            profaktura.getActivityList().add(new Activity("Slanje profakture"));

            processService.save(prodaja);

            Process katalogP = new Process("Prijem kataloga", nabavka, false);
            Process narucivanje = new Process("Narucivanje", nabavka, false);

            Process profakturaP = new Process("Prijem profakture", nabavka, true);
            profakturaP.getActivityList().add(new Activity("Prijem profakture"));
            profakturaP.getActivityList().add(new Activity("Formiranje naloga za placanje"));

            processService.save(nabavka);

            Process zalihe = new Process("Izvestavanje o stanju na zalihama", skladistenje, true);
            zalihe.getActivityList().add(new Activity("Formiranje izvestaja o stanju na zalihama"));
            zalihe.getActivityList().add(new Activity("Formiranje naloga za nabavku"));

            Process otpremanje = new Process("Otpremanje robe", skladistenje, true);
            otpremanje.getActivityList()
                      .add(new Activity("Azuriranje stanja gotovih proizvoda i formiranje otpremnice"));
            otpremanje.getActivityList().add(new Activity("Isporucivanje robe"));

            Process prijem = new Process("Projem robe", skladistenje, true);
            prijem.getActivityList().add(new Activity("Prijem fakture"));
            prijem.getActivityList().add(new Activity("Prijem otpremnice"));
            prijem.getActivityList().add(new Activity("Azuriranje stanja gotovih proizvoda i formiranje prijemnice"));
            prijem.getActivityList().add(new Activity("Overa otpremnice dobavljaca"));

            Process nalogZaNabavku = new Process("Formiranje naloga za nabavku", skladistenje, false);
            processService.save(skladistenje);

            Process obrada = new Process("Obrada izvoda stanja na racunu", finansije, true);
            obrada.getActivityList().add(new Activity("Evidentiranje izvoda stanja sa racuna"));
            obrada.getActivityList().add(new Activity("Evidentiranje uplate"));
            obrada.getActivityList().add(new Activity("Evidentiranje isplate"));
            obrada.getActivityList().add(new Activity("Obrada isplate"));

            Process uplata = new Process("Uplate profakture dobavljaca", finansije, true);
            uplata.getActivityList().add(new Activity("Slanje naloga za prenos"));
            uplata.getActivityList().add(new Activity("Formiranje naloga za prenos"));
            uplata.getActivityList().add(new Activity("Evidentiranje overenih naloga za prenos"));

            processService.save(finansije);

        }

    }
}
