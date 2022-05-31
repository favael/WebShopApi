package com.WebShop.book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private final List<Optional<Book>> shoppingCardList = new ArrayList<Optional<Book>>();

    @GetMapping
    @CrossOrigin
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/szachy")
    @CrossOrigin
    public Stream<Book> getChessBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.CHESS.categoryName));
    }

    @GetMapping("/romans")
    @CrossOrigin
    public Stream<Book> getRomanticBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.ROMANCE.categoryName));
    }

    @GetMapping("/geografia")
    @CrossOrigin
    public Stream<Book> getGeographyBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.GEOGRAPHY.categoryName));
    }

    @GetMapping("/dramat")
    @CrossOrigin
    public Stream<Book> getDramaBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.DRAMA.categoryName));
    }

    @GetMapping("/scfi")
    @CrossOrigin
    public Stream<Book> getScfiBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.SCIFI.categoryName));
    }

    @GetMapping("/gotowanie")
    @CrossOrigin
    public Stream<Book> getCookBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.COOK.categoryName));
    }

    @GetMapping("/historia")
    @CrossOrigin
    public Stream<Book> getHistroryBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.HISTORY.categoryName));
    }

    @PostMapping
    @CrossOrigin
    public Book addbook(@RequestBody Book book) {
        bookRepository.save(book);
        return book;
    }

    @GetMapping("/{isbn}")
    @CrossOrigin
    public Optional<Book> getBook(@PathVariable int isbn) {

        return bookRepository.findById(isbn);
    }

    @DeleteMapping("/{isbn}")
    @CrossOrigin
    public void deleteBook(@PathVariable int isbn) {
        bookRepository.deleteById(isbn);
    }

    @PutMapping()
    @CrossOrigin
    public Book updateBook(@RequestBody Book book) {

        Book updateBook = bookRepository.getOne(book.getIsbn());
        book.setQuantity(updateBook.getQuantity());
        book.setAuthor(updateBook.getAuthor());
        book.setDescription(updateBook.getDescription());
        book.setTitle(updateBook.getTitle());
        book.setPrize(updateBook.getPrize());
        bookRepository.save(updateBook);
        return updateBook;
    }

    @PostMapping("/shoppingCardList/{isbn}")
    @CrossOrigin
    public Optional<Book> addToCardList(@PathVariable int isbn) {
        final Optional<Book> byId = bookRepository.findById(isbn);

        shoppingCardList.add(byId);
    return byId;
    }
    @GetMapping("/shoppingCardList")
    @CrossOrigin
    public List<Optional<Book>> getShoppingCardList() {
        return shoppingCardList;
    }
    @DeleteMapping("/shoppingCardList")
    @CrossOrigin
    public boolean delShoppingCardList() {
        return shoppingCardList.removeAll(shoppingCardList);
    }

    @DeleteMapping("/shoppingCardList/{isbn}")
    @CrossOrigin
    public boolean delShoppingCardListById(@PathVariable int isbn) {
        final List<Boolean> collect = shoppingCardList.stream()
                .map(book -> book.get().getIsbn() == isbn)
                .collect(Collectors.toList());

        return shoppingCardList.removeAll(shoppingCardList);
    }

    @RequestMapping("/findAll")
    public List<Book> findBySearch (String key) {
        return bookRepository.findall(key);
    }


    @PostConstruct
    @CrossOrigin
    public void exampleBase() {

        Book[] books = {
                new Book(BooksCategory.CHESS.categoryName, "Agresywne szachy", "Dobrze się stało, że wydawnictwo „Penelopa” sięgnęło po tłumaczenia wartościowych książek, które są tak potrzebne polskim szachistom. Autorem przedstawionej pracy jest duński arcymistrz mieszkający na stałe w Szkocji. Jacob Aagaard omawia w niej różne problemy prowadzenia ataku w partii szachowej. Jest to więc doskonały podręcznik dla tych, co chcą poznać bliżej ważne motywy taktyczne najczęściej spotykane w praktyce turniejowej. Zawodnicy bardziej zaawansowani mogą natomiast pogłębić wiedzę o kombinacyjnej grze. Oczywiście szkoleniowcy mają tutaj gotowy materiał do zajęć z młodzieżą. Tematyczny podział książki: Zmasowanymi siłami do ataku (Rozdział pierwszy) Nie tracić pary (Rozdział drugi) Dodać nieco koloru do swojej gry (Rozdział trzeci) Względna wartość figur się liczy! (Rozdział czwarty) Uderz tam, gdzie zaboli (Rozdział piąty) Gryząc glebę (Rozdział szósty) Ewolucja/rewolucja (Rozdział siódmy) 15 wielkich przykładów ataku (Rozdział ósmy) Nie zapomnij zrobić następnego kroku (Rozdział dziewiąty),Każdy rozdział rozpoczyna się ćwiczeniami wprowadzającymi. Każdy rozdział rozpoczyna się ćwiczeniami wprowadzającymi.", "Kasparov Garry", 10, 29.99, "agresywne_szachy.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "Obrona francuska", "Wiktor Korcznoj, Aleksander Kalinin Dwukrotny wicemistrz świata Wiktor Korcznoj napisał książkę swojego życia. Zawarł w niej wyniki blisko siedemdziesięciu lat studiowania i grania jednego z najpopularniejszych debiutów. Książka zawiera 72 przeanalizowane partie, kluczowe dla zrozumienia tego trudnego debiutu. Zamiast wkuwania na pamięć wariantów , autorzy proponują najważniejsze plany dla obu stron. Pozycja niezbędna w bibliotece każdego szachisty, grającego obronę francuską czarnymi lub zaczyna partię 1. e2-e4", "Karpov Anatoly", 30, 29.99, "french.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "Obrona Holenderska", "Do you prefer to avoid symmetrical games against white’s 1. d4? And do sharp and complex positions not frighten you? If the answer is yes, the Modernized Dutch Defense might be a worthy surprise weapon to add to your opening repertoire!", "Majdan", 5, 50.99, "dutch.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "Mój System", "W naszej ofercie nie może zabraknąć tej pozycji, rzetelne przestudiowanie tej książki na pewno spowoduje podniesienie siły gry i zrozumienia szachów. Uważana przez wielu czołowych szachistów świata jako jedna z najważniejszych książek w historii szachów - ścisłe TOP 10 literatury szachowej. W książce poruszono wiele ciekawych tematów dających inne spojrzenie: wolny pion , lawirowanie, wymiana i łańcuch pionów. Szachowy elementarz strategii i gry pozycyjnej,napisany z dużym poczuciem humoru. Najbardziej znana książka szachowa na świecie. Na temat tej książki już wszystko zostało napisane. Przeczytaj recenzję Pana Jerzego Konikowskiego Mój system Aron Nimzowitsch. Ilość stron: 456 Okładka: miękka Format książki: 16 x 23 cm ", "Nimzowitsch Aron", 11, 90.99, "moj system.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "Obrona Sycylijska", "Wariant Richtera-Rauzera w obronie sycylijskiej znajduje się w bogatym repertuarze debiutowym najwybitniejszych szachistów świata XX i XXI wieku.Książka powstała z myślą o początkujących szachistach, którzy nie od razu muszą wiedzieć wszystko. Analizuje kontynuacje, które, zdaniem autora, są najważniejsze.W pierwszej części książki omawiane są poszczególne posunięcia. W drugiej - zaprezentowane są przykładowe partie. Całość, dla łatwiejszego poruszania się po poszczególnych wariantach, poprzedzono diagramami.", "Damazy Sowiecki", 2, 40.99, "obrona sycylijska.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "Obrona Skandynawska", "Nie ma żadnych wątpliwości, że podstawą sukcesu w każdej partii szachowej jest pomyślny start.", "Jerzy  Konikowski", 9, 66.99, "obrona skandynawska.jpeg"),
                new Book(BooksCategory.DRAMA.categoryName, "Romeo i Julia", "Chyba nie ma osoby, która nie słyszałaby opowieści o płomiennej, skazanej na klęskę i potępienie miłości kochanków z Verony. Zanurz się w świecie stworzonym przez Williama Szekspira i jeszcze raz odkryj historię Romea i Julii. Opowieści takie jak ta nigdy się nie starzeją. Historia kochanków pochodzących z dwóch zwaśnionych rodów, których potajemne uczucie nieuchronnie prowadzi do tragedii...W swoim najsłynniejszym dziele William Szekspir wykreował miłość niemal idealną – zakazaną i czystą. Karty książki wypełniają ciekawe postacie, akcja toczy się wartko, a czytelnik dosłownie przenosi się w świat szesnastowiecznych Włoch. Dramaty Szekspira to przede wszystkim jednak piękny, konsekwentny i barwny styl literacki. Tragedia porusza, obok wątku romantycznego, także wiele innych, skłaniających do refleksji problemów. Znajdziemy tutaj rozważania na temat możliwości zmiany przeznaczenia, walki z Bogiem czy znaczenia życia ludzkiego. To jedna z tych książek, którą warto przeczytać w życiu kilkukrotnie po to, aby za każdym razem odkryć w niej nowe wartości i niedostrzeżone wcześniej wątki. Nowe wydanie dramatu wyróżnia szlachetny papier oraz piękna szata graficzna utrzymana w barokowym stylu. Twarda oprawa, kartki szyte mocnymi nićmi i odporne na zagniecenia nie tylko zachwycają jakością, ale także gwarantują trwałość książki, która będzie stanowiła ozdobę każdej biblioteki. Wydanie to z pewnością zachęci czytelników, którzy jeszcze nie znają tej wspaniałej lektury, do uzupełnienia braków. Ponadto pozycja ta na pewno będzie się doskonale prezentowała w domowych zbiorach.", "William Shakespeare", 10, 120.55, "romeo.jpeg"),
                new Book(BooksCategory.COOK.categoryName, "Domowe przepisy", "Smacznie, niedrogo i szybko! A przede wszystkim: lekko i zdrowo.Trzymasz w ręku pierwszą książkę o gotowaniu stworzoną wspólnie przez Ewę Chodakowską i Tomka Woźniaka. Ewy przedstawiać nie trzeba, Tomka możesz znać z kulinarnego programu telewizyjnego, jaki prowadzą razem od kilku sezonów. Poznaj bliżej ich filozofię gotowania, na konkretnych – smacznych! – przykładach. Znajdziesz w tej książce 37 ulubionych przepisów Ewy i Tomka na śniadania, przekąski, dania mięsne i wegetariańskie oraz desery. Przepisy opracowali wspólnie – Tomek wniósł swoja pasję, kunszt doświadczonego kucharza oraz inspiracje, jakie przywiózł z podróży po świecie, a Ewa oraz współpracująca z nią specjalistka żywienia zadbały by prezentowane dania były lekkie i zdrowe. Wszystkie przygotujesz szybko z dostępnych wszędzie niedrogich składników. Czego chcieć więcej? Chyba tylko towarzystwa bliskich sercu osób… Smacznego!i", "Ewa Chodakowska", 3, 26.99, "gotuj z nami.jpeg"),
                new Book(BooksCategory.HISTORY.categoryName, "Kłopoty małego liska", "Pięknie ilustrowana opowiastka o małym lisku Rudasku i jego przyjaciółce Kasi. Kasia i Grześ mieszkali na farmie u swoich dziadków. Pewnego zimowego poranka Kasia znalazła w śniegu małego liska.", "Wioletta Święcińska", 9, 6.99, "lisek.jpeg"),
                new Book(BooksCategory.SCIFI.categoryName, "Solar War", "Explore the final stages of The Horus Heresy in this fantastic series, a must have for all fans! New York Times Bestselling Series. After seven years of bitter war, the end has come at last for the conflict known infamously as the Horus Heresy. Terra now lies within the Warmaster’s sights, the Throneworld and the seat of his father’s rule. Horus’ desire is nothing less than the death of the Emperor of Mankind and the utter subjugation of the Imperium. He has become the ascendant vessel of Chaos, and amassed a terrible army with which to enact his will and vengeance. But the way to the Throne will be hard as the primarch Rogal Dorn, the Praetorian and protector of Terra, marshals the defences. First and foremost, Horus must challenge the might of the Sol System itself and the many fleets and bulwarks arrayed there. To gain even a foothold on Terran soil, he must first contend the Solar War. Thus the first stage of the greatest conflict in the history of all mankind begins.", "John French", 10, 78.99, "solar war.jpeg"),
                new Book(BooksCategory.SCIFI.categoryName, "Diuna", "Arrakis, zwana Diuną, to jedyne we wszechświecie źródło melanżu. Z rozkazu Padyszacha Imperatora planetę przejmują Atrydzi, zaciekli wrogowie władających nią dotychczas Harkonnenów. Zwycięstwo księcia Leto Atrydy jest jednak pozorne – przejęcie planety ukartowano. W odpowiedzi na atak Imperium i Harkonnenów dziedzic rodu Atrydów Paul staje na czele rdzennych mieszkańców Diuny i sięga po imperialny tron. Oszałamiające połączenie przygody i mistycyzmu, ekologii i polityki. „Diuna” otrzymała dwie najbardziej prestiżowe nagrody SF: Nebula i Hugo.", "Herbert Frank", 21, 23.46, "diuna.jpeg"),
                new Book(BooksCategory.SCIFI.categoryName, "Wydech", "Wszechświat zaczął się jako potężny oddech, wstrzymywany w płucach… W tych dziewięciu zdumiewająco oryginalnych, prowokujących i wzruszających opowiadaniach Ted Chiang rozważa niektóre z najstarszych pytań stawianych przez ludzkość, a także inne, zupełnie nowe pytania, które mogły przyjść na myśl wyłącznie jemu. W opowiadaniu „Kupiec i wrota alchemika” czasowy portal zmusza sprzedawcę tkanin z dawnego Bagdadu do stawiania czoła starym błędom, dając mu drugą szansę. W tytułowym Wydechu obcy uczony dokonuje zdumiewającego odkrycia o uniwersalnych konsekwencjach. W noweli Lęk to zawrót głowy od wolności możliwość zajrzenia do alternatywnych wszechświatów umożliwia bohaterom zupełnie nowe spojrzenie na kwestię wolnej woli", "Chiang Ted", 12, 49.99, "wydech.jpeg"),
                new Book(BooksCategory.ROMANCE.categoryName, "Seks nie miłość", "„Seks, nie miłość” to kolejny, pełen namiętności romans autorki bestsellerów Vi Keeland. Nat jest po rozwodzie i właśnie układa swoje życie na nowo. Chce uporać się z problemami przeszłości i ostatnia rzecz, na jaką ma teraz ochotę, to nowy związek. Życie pisze dla niej jednak zupełnie inny scenariusz. Na przyjęciu weselnym swoich przyjaciół poznaje Huntera. Ten nieprzeciętnie przystojny i pociągający facet stawia sobie za cel uwiedzenie Nat. Kobieta szybko jednak przejrzała jego zamiary. W Hunterze widzi klasycznego łamacza serc, polującego na kobiety. Kiedy następnego ranka Nat budzi się z nim w jednym łóżku, pozostawia mu nieprawdziwy numer telefonu. Przypadkowy seks to nie jest powód, dla którego miałaby zmieniać swoje plany. Szybko znika z jego życia. Przynajmniej tak jej się wydaje. Mija dziewięć miesięcy, a kochankowie znów się spotykają. Tym razem Hunter nie pozwoli się nabrać na ten sam numer. Teraz to Hunter ma dla Nat propozycje, której nie może odrzucić. Książka  „Seks, nie miłość” pochłonie uwagę czytelniczki lub czytelnika na wiele godzin. Sprawdź, jaką tajemnicę skrywa w sobie Hunter i jak potoczą się losy tych bohaterów!", "Keeland Vi", 21, 26.60, "seks nie milosc.jpeg"),
                new Book(BooksCategory.ROMANCE.categoryName, "Hopeless", "Wydawać by się mogło, że o miłości napisano już wszystko, wykorzystując te wszystkie słowa, które przekazują blask i magię uczuć. Colleen Hoover, autorka „Hopeless”, udowodniła światu, że można zrobić to jeszcze lepiej, piękniej, bardziej intensywnie. Można dotknąć prostych pozornie spraw, używając tak niepozornych środków, które przesuwają jądro Ziemi, nadając jej zupełnie nową orbitę. Miałem wrażenie, że po „Morzu spokoju” - książce Katji Millay (której recenzję znajdziecie tutaj) bardzo długo nic nie będzie w stanie mnie zaskoczyć, poruszyć czy też dotknąć. Sądziłem, że już wtedy nadmiar emocji przelał się przez duszę morzem spokoju, dotykając serca. Było to w sumie nie tak dawno. Historia Sky zaczarowała mnie bardziej, niż mógłbym przypuszczać. Poruszyła we mnie, dojrzałym mężczyźnie wszystkie, nawet te najbardziej delikatne, struny. Krótkie wprowadzenie zawarte na dwóch początkowych stronach powieści jawi się z początku jako wyrwane z kontekstu i nie mówi czytelnikowi zupełnie nic. W miarę upływu przeczytanych stron staje się jednak przejrzystym, a także emocjonalnym, odzwierciedleniem uczuciowej walki Sky. Biorąc do ręki książkę, jeszcze przed rozpoczęciem lektury, uwagę przyciąga okładka, a także wyjątkowo wymowne jej skrzydełka. W trakcie czytania ten krótki graficzny przekaz nabiera szczególnego - pod każdym względem - kształtu. Sky i Six, Six i Sky, chwilę później także Holder i nagle gdzieś pomiędzy nimi wyłania się z mroków nicości Hope. Dwa miesiące w trakcie, których przesunęło się dla Sky jądro Ziemi w osobie Holdera. Wszystko w odpowiednim momencie zaczyna być zrozumiałe. Miłość, by przetrwać zostaje wystawiona na wielką, życiową próbę, gdy do Sky zaczynają docierać przebłyski jej dziecięcej świadomości, odsuniętej lata temu w niebyt nicości. Coś, co nigdy nie miało i nie powinno mieć miejsca z siłą tsunami niszczy spokojne, pełne miłości i radości życie Sky. Six tak bardzo potrzebna w tym momencie wspiera ją przez ocean w wyjątkowy sposób, a kochająca matka staje się największym wrogiem Sky. Trzeba będzie wielkiej odwagi, aby ponownie spojrzeć jej w oczy. Wydawca w formie zachęty do przeczytania książki zaanonsował niesamowitość „Hopeless” wypowiedziami kilkunastu czytelniczek. Dziwić może fakt, że nie ma wśród nich, choć jednego męskiego spojrzenia. Holder przesuwa do mnie dłoń i dotyka swoim małym palcem mojego, zupełnie, jakby nie miał siły chwycić mnie za całą rękę (tak się wzruszyłem mnogością doznań, że nawet trudno mi przepisać ten krótki tekst). To miłe, przedtem trzymaliśmy się już za ręce, za same palce – nigdy. Uświadamiam sobie, że to kolejny pierwszy raz. Wcale nie czuję się rozczarowana, ponieważ wiem, że w jego wypadku te pierwsze razy nie mają znaczenia. Niezależnie od tego, czy będzie mnie całował po raz pierwszy, dwudziesty czy milionowy, nic mnie to nie będzie obchodzić, ponieważ mam pewność, że już i tak do nas należy najlepszy pierwszy pocałunek w historii pierwszych pocałunków – chociaż nawet się nie całowaliśmy. Skoro wszystkie dobre słowa już padły warto zanurzyć dłonie w urzekającym nurcie tej opowieści, słuchając uważnie tego, co ma nam do powiedzenia Sky. A robi to w absolutnie niesamowity sposób!", "Colleen Hoover", 20, 29.24, "hopeless.jpeg"),
                new Book(BooksCategory.ROMANCE.categoryName, "50 twarzy Greya", "Kiedy studentka literatury Anastasia Steele przychodzi, by przeprowadzić wywiad z Christianem Greyem, spotyka mężczyznę pięknego, błyskotliwego i onieśmielającego. Naiwna i niewinna Ana z przerażeniem uświadamia sobie, że pragnie tego mężczyzny. Pomimo zagadkowego dystansu, Ana odkrywa, że rozpaczliwie chce się do niego zbliżyć. Nie potrafiąc oprzeć się delikatnej urodzie Any, jej inteligencji i niespokojnemu duchowi, Grey przyznaje, że także jej pragnie… aczkolwiek na własnych warunkach. Zaszokowana, choć podekscytowana osobliwym gustem erotycznym Greya, Ana się waha. Pomimo jego autentycznych sukcesów- międzynarodowych interesów, ogromnego bogactwa i kochającej rodziny – Greya dręczą demony oraz nieposkromiona potrzeba sprawowania kontroli. Kiedy para rozpoczyna fizycznie śmiały i namiętny romans, Ana odkrywa tajemnice Christiana Greya i zgłębia swoje własne mroczne żądze.", "E L James", 100, 18.99, "50 twarzy greya.jpeg"),
                new Book(BooksCategory.COOK.categoryName, "Super proste gotowanie", "Kuchnia prosta, a raczej… SUPERPROSTA. Oryginalność i wspaniałe smaki kuchni śródziemnomorskiej zamknięte w jednej książce… Mnóstwo przepisów, bardzo prostych w wykonaniu i zawierających nie więcej niż 6 składników. Od przystawek aż po słodkości, przez makarony, zupy, drugie dania z mięsa i ryb, nie wspominając już o pizzy i innych słonych wypiekach. Superproste gotowanie oznacza używanie niewielu, ale za to właściwych produktów. Oznacza także ograniczanie marnotrawstwa i oszczędzanie czasu. Prosta kuchnia to pewna filozofia życia, która przede wszystkim czyni je lepszym.", "Opracowanie zbiorowe", 3, 40.22, "super proste.jpeg"),
                new Book(BooksCategory.HISTORY.categoryName, "Nasza Wojna 1", "Odkrywcza historia frontów wschodnich I wojny światowej. Chyba każdy słyszał o Sommie, Verdun czy Marnie, miejscach najważniejszych bitew frontu zachodniego I wojny światowej. Niewiele osób pamięta jednak o co najmniej równie ważnych miejscach na mapach frontu wschodniego – Gorlicach, Przasnyszu czy Przemyślu. Ta książka przywraca pamięć o tych jakże przecież istotnych dla naszych dziejów wydarzeniach. Nasza wojna. Europa Środkowo-Wschodnia 1914–1918. Imperia nie jest typową publikacją historyczną. Chociaż jej autorzy z wielką skrupulatnością przedstawiają dyplomatyczne przyczyny i okoliczności wybuchu I wojny światowej oraz dokładnie opisują towarzyszące jej działania militarne, to nie polityka międzynarodowa jest przedmiotem tej książki. O jej wyjątkowości stanowi fakt, że koncentruje się ona przede wszystkim na historiach ludzi, zarówno jednostek, jak i społeczeństw, i wpływie, jaki wywarły na nich wojenne doświadczenia. Dwaj historycy, Włodzimierz Borodziej i Maciej Górny, wykonali olbrzymią pracę, by w przystępny sposób przybliżyć czytelnikowi ten fragment dziejów. Książka zawiera liczne fragmenty listów i wspomnień uczestników działań na froncie wschodnim oraz cytaty z dzieł literackich z okresu wojny, które służą za ilustrację ówczesnego sposobu postrzegania rzeczywistości. Dzięki żywemu językowi i plastycznym opisom publikacja jest interesująca nie tylko dla specjalistów.", "Górny Maciej", 4, 35.99, "nasza wojna1.jpeg"),
                new Book(BooksCategory.HISTORY.categoryName, "Nasza Wojna 2", "Drugi tom fascynującej monografii I wojny światowej, która otworzyła Polsce drogę do odzyskania niepodległości w 1918 roku. Wielką Wojnę na wschodzie Europy wywołały imperia, biły się w niej narody a ginęli zwykli ludzie. Im dłużej trwała, tym trudniej było wyobrazić sobie, jak się skończy i czy w ogóle kiedyś nastanie pokój. Tego, że zmianie ulegnie prawie wszystko, nie przewidział nikt. Drugi tom Naszej wojny opisuje tę wielką zmianę na wielu polach. Opowiada o nowej taktyce prowadzenia działań zbrojnych, o strajkach i buntach robotniczych, prawach kobiet, kulturze, polityce i walce o niepodległość narodów Europy Środkowo-Wschodniej i Bałkanów. Wszystko to w porywającym stylu - przystępnie, czasem dowcipnie, zawsze w oparciu o źródła, w tym dotychczas rzadko wykorzystywane.", "Górny Maciej", 4, 37.99, "nasza wojna2.jpeg"),
                new Book(BooksCategory.DRAMA.categoryName, "Za żadne skarby", "Życie ma z reguły inne plany niż my. Ewa spełniła swoje marzenie – wyrwała się z małej mazurskiej wsi, skończyła mikrobiologię. Właśnie wybiera się na staż naukowy do Paryża. Ma też całkiem sensownego faceta. I nagle wszystko się kończy. Jeden telefon uruchamia bieg zdarzeń, jakich Ewa nigdy by się nie spodziewała. Dziewczyna wraca do zaściankowej rzeczywistości, do pijącego ojca i chorego brata. Przed nią wiele problemów, ale też miłość do utraty tchu. Co zrobi Ewa? Jedno jest pewne: za żadne skarby się nie podda! Sięgnij po książkę, w której znajdziesz kobiecą siłę i odważną namiętność, dramatyczne decyzje i nieczyste interesy, zabawną, ale i nielukrowaną stronę życia. Za żadne skarby to powieść, jakiej jeszcze nie było. Vera Falski to kobieta z krwi i kości. Znasz ją. Może być twoją sąsiadką. Przygląda się sobie i innym kobietom z polskich ulic, uliczek i ścieżek. Z jej obserwacji, refleksji i marzeń powstała ta książka. Vera Falski to tysiące kobiet zaklętych w jednym piórze. Może twoja historia będzie inspiracją do jej następnej powieści…", "Falski Vera", 8, 64.77, "za zadne skarby.jpeg"),
                new Book(BooksCategory.DRAMA.categoryName, "Miłość przychodzi z deszczem", "Porywająca opowieść o więzach krwi i serc. Marek i Marcin, jak to bliźniacy, byli nierozłączni. Do czasu. Marek rzuca pracę i wyjeżdża bez słowa. Drugi z braci milczy. Po kilku miesiącach Marek decyduje się wrócić. Przyjeżdża z Anią, dziewczyną, którą uważa za tę jedną jedyną, wymarzoną. Jest przerażony, gdy odkrywa, że jego bratowa jest w ciąży. Nim zdąży podjąć decyzję, co zrobić, ginie w wypadku. Kilka lat po tym tragicznym wydarzeniu Ania wraca do Poznania i spotyka drugiego z braci. Tylko czy na pewno?", "Mila Rudnik", 11, 19.69, "milosc przychodzi z deszczem.jpeg"),
                new Book(BooksCategory.DRAMA.categoryName, "We dwoje", "Pełna pogody ducha Karolina Tyrolska od zawsze wierzyła, że los jej sprzyja. Miała kochającego męża, satysfakcjonującą pracę, stabilizację finansową, więc mimo problemów z zajściem w ciążę, była pewna, że prędzej czy później uda się zrealizować jej marzenie. Pewien lipcowy dzień wystawił ją na niespodziewaną próbę. Po raz pierwszy życie przestaje układać się po jej myśli i Karolina czuje, że traci grunt pod nogami. W jej świat wkraczają nowe postacie, inne schodzą na drugi plan. Zagubiona i przerażona pozwala, by bliscy decydowali za nią. A każdy ma inny sposób na wybawienie jej z opresji…Tylko czy to jeszcze jest jej życie?", "Woźniczka Katarzyna", 10, 27.50, "we dwoje.jpeg")
        };

        Arrays.stream(books)
                .forEach(bookRepository::save);

    }
}


