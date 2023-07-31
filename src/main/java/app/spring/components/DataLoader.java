package app.spring.components;

import app.spring.entity.Province;
import app.spring.entity.authentication.Role;
import app.spring.entity.authentication.User;
import app.spring.repository.ProvinceRepository;
import app.spring.repository.authentication.RoleRepository;
import app.spring.repository.authentication.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProvinceRepository provinceRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    public DataLoader(UserRepository userRepository,
                      RoleRepository roleRepository,
                      ProvinceRepository provinceRepository,
                      PasswordEncoder passwordEncoder,
                      EntityManager entityManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.provinceRepository = provinceRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean roleExists = userRepository.count() > 0;
        boolean userExists = userRepository.count() > 0;
        boolean provinceExists = provinceRepository.count() > 0;

        if (!roleExists) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);

            if (!userExists) {
                User superUser = new User();
                superUser.setName("Superuser");
                superUser.setUsername("superuser");
                superUser.setEmail("superuser@example.com");
                superUser.setPassword(passwordEncoder.encode("2lr74A%S"));
                superUser.setConfirmPassword(passwordEncoder.encode("2lr74A%S"));
                superUser.setIsEnabled(true);
                Set<Role> roles1 = new HashSet<>();
                roles1.add(adminRole);
                superUser.setRoles(roles1);
                entityManager.persist(adminRole);
                userRepository.save(superUser);

                User adminUser = new User();
                adminUser.setName("Admin");
                adminUser.setUsername("admin");
                adminUser.setEmail("admin@example.com");
                adminUser.setPassword(passwordEncoder.encode("2lr74A%S"));
                adminUser.setConfirmPassword(passwordEncoder.encode("2lr74A%S"));
                adminUser.setIsEnabled(true);
                Set<Role> roles2 = new HashSet<>();
                roles2.add(adminRole);
                adminUser.setRoles(roles2);
                entityManager.persist(adminRole);
                userRepository.save(adminUser);
            }
        }

        if (!provinceExists) {
            Province bangkok = new Province();
            bangkok.setName("กรุงเทพมหานคร");
            bangkok.setNameEnglish("Bangkok");
            bangkok.setCode("กทม");
            bangkok.setCodeEnglish("BKK");
            bangkok.setRegion("ภาคกลาง");
            bangkok.setRegionEnglish("Central Region");
            provinceRepository.save(bangkok);

            Province kamphaengPhet = new Province();
            kamphaengPhet.setName("กำแพงเพชร");
            kamphaengPhet.setNameEnglish("Kamphaeng Phet");
            kamphaengPhet.setCode("กพ");
            kamphaengPhet.setCodeEnglish("KPT");
            kamphaengPhet.setRegion("ภาคกลาง");
            kamphaengPhet.setRegionEnglish("Central Region");
            provinceRepository.save(kamphaengPhet);

            Province chaiNat = new Province();
            chaiNat.setName("ชัยนาท");
            chaiNat.setNameEnglish("Chai Nat");
            chaiNat.setCode("ชน");
            chaiNat.setCodeEnglish("CNT");
            chaiNat.setRegion("ภาคกลาง");
            chaiNat.setRegionEnglish("Central Region");
            provinceRepository.save(chaiNat);

            Province nakhonNayok = new Province();
            nakhonNayok.setName("นครนายก");
            nakhonNayok.setNameEnglish("Nakhon Nayok");
            nakhonNayok.setCode("นย");
            nakhonNayok.setCodeEnglish("NYK");
            nakhonNayok.setRegion("ภาคกลาง");
            nakhonNayok.setRegionEnglish("Central Region");
            provinceRepository.save(nakhonNayok);

            Province nakhonPathom = new Province();
            nakhonPathom.setName("นครปฐม");
            nakhonPathom.setNameEnglish("Nakhon Pathom");
            nakhonPathom.setCode("นฐ");
            nakhonPathom.setCodeEnglish("NPT");
            nakhonPathom.setRegion("ภาคกลาง");
            nakhonPathom.setRegionEnglish("Central Region");
            provinceRepository.save(nakhonPathom);

            Province nakhonSawan = new Province();
            nakhonSawan.setName("นครสวรรค์");
            nakhonSawan.setNameEnglish("Nakhon Sawan");
            nakhonSawan.setCode("นว");
            nakhonSawan.setCodeEnglish("NSN");
            nakhonSawan.setRegion("ภาคกลาง");
            nakhonSawan.setRegionEnglish("Central Region");
            provinceRepository.save(nakhonSawan);

            Province nonthaburi = new Province();
            nonthaburi.setName("นนทบุรี");
            nonthaburi.setNameEnglish("Nonthaburi");
            nonthaburi.setCode("นบ");
            nonthaburi.setCodeEnglish("NBI");
            nonthaburi.setRegion("ภาคกลาง");
            nonthaburi.setRegionEnglish("Central Region");
            provinceRepository.save(nonthaburi);

            Province pathumThani = new Province();
            pathumThani.setName("ปทุมธานี");
            pathumThani.setNameEnglish("Pathum Thani");
            pathumThani.setCode("ปท");
            pathumThani.setCodeEnglish("PTE");
            pathumThani.setRegion("ภาคกลาง");
            pathumThani.setRegionEnglish("Central Region");
            provinceRepository.save(pathumThani);

            Province phraNakhonSiAyutthaya = new Province();
            phraNakhonSiAyutthaya.setName("พระนครศรีอยุธยา");
            phraNakhonSiAyutthaya.setNameEnglish("Phra Nakhon Si Ayutthaya");
            phraNakhonSiAyutthaya.setCode("อย");
            phraNakhonSiAyutthaya.setCodeEnglish("AYA");
            phraNakhonSiAyutthaya.setRegion("ภาคกลาง");
            phraNakhonSiAyutthaya.setRegionEnglish("Central Region");
            provinceRepository.save(phraNakhonSiAyutthaya);

            Province phichit = new Province();
            phichit.setName("พิจิตร");
            phichit.setNameEnglish("Phichit");
            phichit.setCode("พจ");
            phichit.setCodeEnglish("PCT");
            phichit.setRegion("ภาคกลาง");
            phichit.setRegionEnglish("Central Region");
            provinceRepository.save(phichit);

            Province phitsanulok = new Province();
            phitsanulok.setName("พิษณุโลก");
            phitsanulok.setNameEnglish("Phitsanulok");
            phitsanulok.setCode("พล");
            phitsanulok.setCodeEnglish("PLK");
            phitsanulok.setRegion("ภาคกลาง");
            phitsanulok.setRegionEnglish("Central Region");
            provinceRepository.save(phitsanulok);

            Province phetchabun = new Province();
            phetchabun.setName("เพชรบูรณ์");
            phetchabun.setNameEnglish("Phetchabun");
            phetchabun.setCode("พช");
            phetchabun.setCodeEnglish("PNB");
            phetchabun.setRegion("ภาคกลาง");
            phetchabun.setRegionEnglish("Central Region");
            provinceRepository.save(phetchabun);

            Province lopBuri = new Province();
            lopBuri.setName("ลพบุรี");
            lopBuri.setNameEnglish("Lop Buri");
            lopBuri.setCode("ลบ");
            lopBuri.setCodeEnglish("LRI");
            lopBuri.setRegion("ภาคกลาง");
            lopBuri.setRegionEnglish("Central Region");
            provinceRepository.save(lopBuri);

            Province samutPrakan = new Province();
            samutPrakan.setName("สมุทรปราการ");
            samutPrakan.setNameEnglish("Samut Prakan");
            samutPrakan.setCode("สป");
            samutPrakan.setCodeEnglish("SPK");
            samutPrakan.setRegion("ภาคกลาง");
            samutPrakan.setRegionEnglish("Central Region");
            provinceRepository.save(samutPrakan);

            Province samutSongkhram = new Province();
            samutSongkhram.setName("สมุทรสงคราม");
            samutSongkhram.setNameEnglish("Samut Songkhram");
            samutSongkhram.setCode("สส");
            samutSongkhram.setCodeEnglish("SKM");
            samutSongkhram.setRegion("ภาคกลาง");
            samutSongkhram.setRegionEnglish("Central Region");
            provinceRepository.save(samutSongkhram);

            Province samutSakhon = new Province();
            samutSakhon.setName("สมุทรสาคร");
            samutSakhon.setNameEnglish("Samut Sakhon");
            samutSakhon.setCode("สค");
            samutSakhon.setCodeEnglish("SKN");
            samutSakhon.setRegion("ภาคกลาง");
            samutSakhon.setRegionEnglish("Central Region");
            provinceRepository.save(samutSakhon);

            Province saraburi = new Province();
            saraburi.setName("สระบุรี");
            saraburi.setNameEnglish("Saraburi");
            saraburi.setCode("สบ");
            saraburi.setCodeEnglish("SRI");
            saraburi.setRegion("ภาคกลาง");
            saraburi.setRegionEnglish("Central Region");
            provinceRepository.save(saraburi);

            Province singBuri = new Province();
            singBuri.setName("สิงห์บุรี");
            singBuri.setNameEnglish("Sing Buri");
            singBuri.setCode("สห");
            singBuri.setCodeEnglish("SBR");
            singBuri.setRegion("ภาคกลาง");
            singBuri.setRegionEnglish("Central Region");
            provinceRepository.save(singBuri);

            Province sukhothai = new Province();
            sukhothai.setName("สุโขทัย");
            sukhothai.setNameEnglish("Sukhothai");
            sukhothai.setCode("สท");
            sukhothai.setCodeEnglish("STI");
            sukhothai.setRegion("ภาคกลาง");
            sukhothai.setRegionEnglish("Central Region");
            provinceRepository.save(sukhothai);

            Province suphanBuri = new Province();
            suphanBuri.setName("สุพรรณบุรี");
            suphanBuri.setNameEnglish("Suphan Buri");
            suphanBuri.setCode("สพ");
            suphanBuri.setCodeEnglish("SPB");
            suphanBuri.setRegion("ภาคกลาง");
            suphanBuri.setRegionEnglish("Central Region");
            provinceRepository.save(suphanBuri);

            Province angThong = new Province();
            angThong.setName("อ่างทอง");
            angThong.setNameEnglish("Ang Thong");
            angThong.setCode("อท");
            angThong.setCodeEnglish("ATG");
            angThong.setRegion("ภาคกลาง");
            angThong.setRegionEnglish("Central Region");
            provinceRepository.save(angThong);

            Province uthaiThani  = new Province();
            uthaiThani.setName("อุทัยธานี");
            uthaiThani.setNameEnglish("Uthai Thani");
            uthaiThani.setCode("อน");
            uthaiThani.setCodeEnglish("UTI");
            uthaiThani.setRegion("ภาคกลาง");
            uthaiThani.setRegionEnglish("Central Region");
            provinceRepository.save(uthaiThani);
        }
    }

}
