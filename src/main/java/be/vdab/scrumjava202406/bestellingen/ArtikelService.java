package be.vdab.scrumjava202406.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ArtikelService {
    private final ArtikelRepository artikelRepository;
    private final MagazijnPlaatsRepository magazijnPlaatsRepository;

    public ArtikelService(ArtikelRepository artikelRepository,MagazijnPlaatsRepository magazijnPlaatsRepository) {
        this.artikelRepository = artikelRepository;
        this.magazijnPlaatsRepository = magazijnPlaatsRepository;
    }

    Optional<Artikel> findById(long id) {
        return artikelRepository.findById(id);
    }

    Optional<Artikel> findByEanLastFive(String eanLastFive) {
        return artikelRepository.findByEanLastFive(eanLastFive);
    }

    List<MagazijnPlaats> findAllPlaatsById(long id) {
        return magazijnPlaatsRepository.findAllPlaatsById(id);
    }

    public NieuweMagazijnPlaats findFirstFreePlace(long magazijnPlaatsId){
        return magazijnPlaatsRepository.findFirstFreePlace(magazijnPlaatsId);
    }
    public Optional<MagazijnPlaats> checkFreeSpaceCanAddedToPlace(long id){
        Artikel artikel = artikelRepository.findById(id).orElseThrow();
        return magazijnPlaatsRepository.checkFreeSpaceCanAddedToPlace(id,artikel.getMaxAantalInMagazijnPlaats());
    }

    @Transactional
    public void updateAantal(long magazijnPlaatsId, int aantal){
        magazijnPlaatsRepository.updateAantal(magazijnPlaatsId, aantal);
    }

    @Transactional
    public MagazijnPlaats updateAantalAndId(long magazijnPlaatsId, long artikelId, int aantal){
        return magazijnPlaatsRepository.updateAantalAndId(magazijnPlaatsId, artikelId, aantal);
    }


    public List<MagazijnPlaats> getAvailablePlacePerArtikelId(long artikelId, int aantal, MagazijnIdDataHolder magazijnIdDataHolder) {
        System.out.println("-----------------------------");
        System.out.println(magazijnIdDataHolder.getMagazijnPlaatsId());
        List<MagazijnPlaats> listVanMagazijnPlaatsen = new ArrayList<>();
        Artikel artikel = artikelRepository.findById(artikelId).orElseThrow();

        checkFreeSpaceCanAddedToPlace(artikelId).ifPresentOrElse(
                (magazijnPlaats)
                        -> {
                    System.out.println("Value is present, its: " + magazijnPlaats);
                    if(artikel.getMaxAantalInMagazijnPlaats() - magazijnPlaats.getAantal() >= aantal){
                        // 25 - 15 >= 5
                        System.out.println("direct plaatsen terug geven");
                        MagazijnPlaats newMagazijnPlaats = new MagazijnPlaats(
                                magazijnPlaats.getMagazijnPlaatsId(),
                                artikelId,
                                magazijnPlaats.getRij(),
                                magazijnPlaats.getRek(),
                                aantal
                        );
                        listVanMagazijnPlaatsen.add(newMagazijnPlaats);
                    }else{
                        System.out.println("for loop");
                        MagazijnPlaats newMagazijnPlaats = new MagazijnPlaats(
                                magazijnPlaats.getMagazijnPlaatsId(),
                                artikelId,
                                magazijnPlaats.getRij(),
                                magazijnPlaats.getRek(),
                                artikel.getMaxAantalInMagazijnPlaats() - magazijnPlaats.getAantal()
                        );
                        listVanMagazijnPlaatsen.add(newMagazijnPlaats);
                        int remainingTotaal = aantal - (artikel.getMaxAantalInMagazijnPlaats()-magazijnPlaats.getAantal());


                        for (int i = 0; i < (remainingTotaal / artikel.getMaxAantalInMagazijnPlaats()) + 1 ; i++) {

                            NieuweMagazijnPlaats nieuweMagazijnPlaats = findFirstFreePlace(magazijnIdDataHolder.getMagazijnPlaatsId() + i);
                            if (i == remainingTotaal / artikel.getMaxAantalInMagazijnPlaats()) {
                               // magazijnIdDataHolder.setMagazijnPlaatsId(nieuweMagazijnPlaats.getMagazijnPlaatsId()+1);
                                if ( (aantal -  (remainingTotaal / artikel.getMaxAantalInMagazijnPlaats() * artikel.getMaxAantalInMagazijnPlaats()) - (artikel.getMaxAantalInMagazijnPlaats() - magazijnPlaats.getAantal()) != 0)){
                                    magazijnIdDataHolder.setMagazijnPlaatsId(magazijnIdDataHolder.getMagazijnPlaatsId() + 1);
                                    listVanMagazijnPlaatsen.add(new MagazijnPlaats(
                                            nieuweMagazijnPlaats.getMagazijnPlaatsId(),
                                            artikelId,
                                            nieuweMagazijnPlaats.getRij(),
                                            nieuweMagazijnPlaats.getRek(),
                                            aantal -
                                                    (remainingTotaal / artikel.getMaxAantalInMagazijnPlaats() * artikel.getMaxAantalInMagazijnPlaats())
                                                    -
                                                    (artikel.getMaxAantalInMagazijnPlaats() - magazijnPlaats.getAantal())
                                    ));
                                }

                            }else{
                               // magazijnIdDataHolder.setMagazijnPlaatsId(nieuweMagazijnPlaats.getMagazijnPlaatsId()+1);
                                listVanMagazijnPlaatsen.add(new MagazijnPlaats(
                                        nieuweMagazijnPlaats.getMagazijnPlaatsId(),
                                        artikelId,
                                        nieuweMagazijnPlaats.getRij(),
                                        nieuweMagazijnPlaats.getRek(),
                                        artikel.getMaxAantalInMagazijnPlaats()
                                ));
                            }
                        }

                        magazijnIdDataHolder.setMagazijnPlaatsId(magazijnIdDataHolder.getMagazijnPlaatsId() + (remainingTotaal / artikel.getMaxAantalInMagazijnPlaats()));

                    }

                },
                ()
                        -> {
                    System.out.println("Value is empty");
                    // long firstAvailablePlaceId = magazijnPlaatsRepository.findFirstFreePlaceId();

                    for (int i = 0; i < (aantal / artikel.getMaxAantalInMagazijnPlaats() + 1) ; i++) {
                        NieuweMagazijnPlaats nieuweMagazijnPlaats = findFirstFreePlace(magazijnIdDataHolder.getMagazijnPlaatsId() + i);
                        //magazijnIdDataHolder.setMagazijnPlaatsId(nieuweMagazijnPlaats.getMagazijnPlaatsId()+i);
                        System.out.println(nieuweMagazijnPlaats);
                        System.out.println(magazijnIdDataHolder.getMagazijnPlaatsId());
                        if (i == aantal / artikel.getMaxAantalInMagazijnPlaats()) {
                            // magazijnIdDataHolder.setMagazijnPlaatsId(nieuweMagazijnPlaats.getMagazijnPlaatsId());
                            if (aantal - (aantal / artikel.getMaxAantalInMagazijnPlaats() * artikel.getMaxAantalInMagazijnPlaats()) != 0 ){
                                //magazijnIdDataHolder.setMagazijnPlaatsId(nieuweMagazijnPlaats.getMagazijnPlaatsId() + 1);
                                magazijnIdDataHolder.setMagazijnPlaatsId(magazijnIdDataHolder.getMagazijnPlaatsId() + 1);
                                listVanMagazijnPlaatsen.add(new MagazijnPlaats(
                                        nieuweMagazijnPlaats.getMagazijnPlaatsId(),
                                        artikelId,
                                        nieuweMagazijnPlaats.getRij(),
                                        nieuweMagazijnPlaats.getRek(),
                                        aantal -
                                                (aantal / artikel.getMaxAantalInMagazijnPlaats() * artikel.getMaxAantalInMagazijnPlaats())
                                ));
                            }
                        }else{
                            //magazijnIdDataHolder.setMagazijnPlaatsId(nieuweMagazijnPlaats.getMagazijnPlaatsId());
                            listVanMagazijnPlaatsen.add(new MagazijnPlaats(
                                    nieuweMagazijnPlaats.getMagazijnPlaatsId(),
                                    artikelId,
                                    nieuweMagazijnPlaats.getRij(),
                                    nieuweMagazijnPlaats.getRek(),
                                    artikel.getMaxAantalInMagazijnPlaats()
                            ));
                        }
                    }
                    magazijnIdDataHolder.setMagazijnPlaatsId(magazijnIdDataHolder.getMagazijnPlaatsId() + (aantal / artikel.getMaxAantalInMagazijnPlaats()));
                }

        );

        System.out.println(listVanMagazijnPlaatsen);
        return listVanMagazijnPlaatsen;

    }

    public List<MagazijnPlaats>  findAllPlaceForDelivery (List<ArtikelPlaatsRequest> artikelPlaatsRequests){
        List<MagazijnPlaats> listVanMagazijnPlaatsen = new ArrayList<>();
        MagazijnIdDataHolder magazijnIdDataHolder = new MagazijnIdDataHolder(magazijnPlaatsRepository.findFirstFreePlaceId());
        for (ArtikelPlaatsRequest artikelPlaatsRequest : artikelPlaatsRequests) {
            listVanMagazijnPlaatsen.addAll(getAvailablePlacePerArtikelId(artikelPlaatsRequest.getArtikelId(), artikelPlaatsRequest.getAantal(), magazijnIdDataHolder));
        }
        // System.out.println(listVanMagazijnPlaatsen);
        return listVanMagazijnPlaatsen;

    }

    @Transactional
    public List<MagazijnPlaats> updateAllPlaceForDelivery(List<MagazijnPlaats> magazijnPlaatsen){
        List<MagazijnPlaats> listVanMagazijnPlaatsen = new ArrayList<>();
        for (MagazijnPlaats magazijnPlaats : magazijnPlaatsen) {
            listVanMagazijnPlaatsen.add(updateAantalAndId(magazijnPlaats.getMagazijnPlaatsId(),magazijnPlaats.getArtikelId(),magazijnPlaats.getAantal()));
        }
        return listVanMagazijnPlaatsen;
    }
    @Transactional
    void updateTotaleVoorraad(long artikelId, AanpassingVoorraadMetAantal aantal) {
        artikelRepository.updateTotaleVoorraadPerArtikel(artikelId, aantal);
    }


    @Transactional
    void updateArtikelVoorraadPerPlaats(List<RijRekNieuweAantal> rijRekNieuweAantalLijst) {
        rijRekNieuweAantalLijst.forEach(artikelRepository::updateMagazijnVoorraad);
    }
}

