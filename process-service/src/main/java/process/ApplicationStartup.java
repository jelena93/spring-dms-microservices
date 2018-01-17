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
            Process prodaja = new Process("Prodaja", null, false,1);
            Process nabavka = new Process("Nabavka", null, false,1);
            Process skladistenje = new Process("Skladistenje i oprema", null, false,1);
            Process finansije = new Process("Regulisanje finansija", null, false,1);

            prodaja = processService.save(prodaja);
            nabavka = processService.save(nabavka);
            skladistenje = processService.save(skladistenje);
            finansije = processService.save(finansije);

            Process katalog = new Process("Formiranje i slanje kataloga", prodaja, true,1);
            katalog.getActivityList().add(new Activity("Formiranje kataloga"));
            katalog.getActivityList().add(new Activity("Slanje kataloga"));
            processService.save(katalog);

            Process narudzbenica = new Process("Prijem narudzbenice", prodaja, false,1);
            Process otprema = new Process("Formiranje naloga za otpremu", prodaja, false,1);

            processService.save(narudzbenica);
            processService.save(otprema);

            Process profaktura = new Process("Formiranje profakture", prodaja, true,1);
            profaktura.getActivityList().add(new Activity("Provera raspolozivosti"));
            profaktura.getActivityList().add(new Activity("Formiranje profakture"));
            profaktura.getActivityList().add(new Activity("Slanje profakture"));

            processService.save(profaktura);

            Process katalogP = new Process("Prijem kataloga", nabavka, false,1);
            Process narucivanje = new Process("Narucivanje", nabavka, false,1);

            processService.save(katalogP);
            processService.save(narucivanje);

            Process profakturaP = new Process("Prijem profakture", nabavka, true,1);
            profakturaP.getActivityList().add(new Activity("Prijem profakture"));
            profakturaP.getActivityList().add(new Activity("Formiranje naloga za placanje"));

            processService.save(profakturaP);

            Process zalihe = new Process("Izvestavanje o stanju na zalihama", skladistenje, true,1);
            zalihe.getActivityList().add(new Activity("Formiranje izvestaja o stanju na zalihama"));
            zalihe.getActivityList().add(new Activity("Formiranje naloga za nabavku"));

            processService.save(zalihe);

            Process otpremanje = new Process("Otpremanje robe", skladistenje, true,1);
            otpremanje.getActivityList()
                      .add(new Activity("Azuriranje stanja gotovih proizvoda i formiranje otpremnice"));
            otpremanje.getActivityList().add(new Activity("Isporucivanje robe"));

            processService.save(otpremanje);

            Process prijem = new Process("Projem robe", skladistenje, true,1);
            prijem.getActivityList().add(new Activity("Prijem fakture"));
            prijem.getActivityList().add(new Activity("Prijem otpremnice"));
            prijem.getActivityList().add(new Activity("Azuriranje stanja gotovih proizvoda i formiranje prijemnice"));
            prijem.getActivityList().add(new Activity("Overa otpremnice dobavljaca"));

            processService.save(prijem);

            Process nalogZaNabavku = new Process("Formiranje naloga za nabavku", skladistenje, false,1);

            processService.save(nalogZaNabavku);

            Process obrada = new Process("Obrada izvoda stanja na racunu", finansije, true,1);
            obrada.getActivityList().add(new Activity("Evidentiranje izvoda stanja sa racuna"));
            obrada.getActivityList().add(new Activity("Evidentiranje uplate"));
            obrada.getActivityList().add(new Activity("Evidentiranje isplate"));
            obrada.getActivityList().add(new Activity("Obrada isplate"));

            processService.save(obrada);

            Process uplata = new Process("Uplate profakture dobavljaca", finansije, true,1);
            uplata.getActivityList().add(new Activity("Slanje naloga za prenos"));
            uplata.getActivityList().add(new Activity("Formiranje naloga za prenos"));
            uplata.getActivityList().add(new Activity("Evidentiranje overenih naloga za prenos"));

            processService.save(uplata);

        }

    }
}
