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

    @GetMapping("/findAll/{key}")
    public List<Book> findBySearch (@PathVariable String key) {
        return bookRepository.findall(key);
    }


    @PostConstruct
    @CrossOrigin
    public void exampleBase() {

        Book[] books = {
                new Book(BooksCategory.CHESS.categoryName, "Agresywne szachy", "Dobrze si?? sta??o, ??e wydawnictwo ???Penelopa??? si??gn????o po t??umaczenia warto??ciowych ksi????ek, kt??re s?? tak potrzebne polskim szachistom. Autorem przedstawionej pracy jest du??ski arcymistrz mieszkaj??cy na sta??e w Szkocji. Jacob Aagaard omawia w niej r????ne problemy prowadzenia ataku w partii szachowej. Jest to wi??c doskona??y podr??cznik dla tych, co chc?? pozna?? bli??ej wa??ne motywy taktyczne najcz????ciej spotykane w praktyce turniejowej. Zawodnicy bardziej zaawansowani mog?? natomiast pog????bi?? wiedz?? o kombinacyjnej grze. Oczywi??cie szkoleniowcy maj?? tutaj gotowy materia?? do zaj???? z m??odzie????. Tematyczny podzia?? ksi????ki: Zmasowanymi si??ami do ataku (Rozdzia?? pierwszy) Nie traci?? pary (Rozdzia?? drugi) Doda?? nieco koloru do swojej gry (Rozdzia?? trzeci) Wzgl??dna warto???? figur si?? liczy! (Rozdzia?? czwarty) Uderz tam, gdzie zaboli (Rozdzia?? pi??ty) Gryz??c gleb?? (Rozdzia?? sz??sty) Ewolucja/rewolucja (Rozdzia?? si??dmy) 15 wielkich przyk??ad??w ataku (Rozdzia?? ??smy) Nie zapomnij zrobi?? nast??pnego kroku (Rozdzia?? dziewi??ty),Ka??dy rozdzia?? rozpoczyna si?? ??wiczeniami wprowadzaj??cymi. Ka??dy rozdzia?? rozpoczyna si?? ??wiczeniami wprowadzaj??cymi.", "Kasparov Garry", 10, 29.99, "agresywne_szachy.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "Obrona francuska", "Wiktor Korcznoj, Aleksander Kalinin Dwukrotny wicemistrz ??wiata Wiktor Korcznoj napisa?? ksi????k?? swojego ??ycia. Zawar?? w niej wyniki blisko siedemdziesi??ciu lat studiowania i grania jednego z najpopularniejszych debiut??w. Ksi????ka zawiera 72 przeanalizowane partie, kluczowe dla zrozumienia tego trudnego debiutu. Zamiast wkuwania na pami???? wariant??w , autorzy proponuj?? najwa??niejsze plany dla obu stron. Pozycja niezb??dna w bibliotece ka??dego szachisty, graj??cego obron?? francusk?? czarnymi lub zaczyna parti?? 1. e2-e4", "Karpov Anatoly", 30, 29.99, "french.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "Obrona Holenderska", "Do you prefer to avoid symmetrical games against white???s 1. d4? And do sharp and complex positions not frighten you? If the answer is yes, the Modernized Dutch Defense might be a worthy surprise weapon to add to your opening repertoire!", "Majdan", 5, 50.99, "dutch.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "M??j System", "W naszej ofercie nie mo??e zabrakn???? tej pozycji, rzetelne przestudiowanie tej ksi????ki na pewno spowoduje podniesienie si??y gry i zrozumienia szach??w. Uwa??ana przez wielu czo??owych szachist??w ??wiata jako jedna z najwa??niejszych ksi????ek w historii szach??w - ??cis??e TOP 10 literatury szachowej. W ksi????ce poruszono wiele ciekawych temat??w daj??cych inne spojrzenie: wolny pion , lawirowanie, wymiana i ??a??cuch pion??w. Szachowy elementarz strategii i gry pozycyjnej,napisany z du??ym poczuciem humoru. Najbardziej znana ksi????ka szachowa na ??wiecie. Na temat tej ksi????ki ju?? wszystko zosta??o napisane. Przeczytaj recenzj?? Pana Jerzego Konikowskiego M??j system Aron Nimzowitsch. Ilo???? stron: 456 Ok??adka: mi??kka Format ksi????ki: 16 x 23 cm ", "Nimzowitsch Aron", 11, 90.99, "moj system.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "Obrona Sycylijska", "Wariant Richtera-Rauzera w obronie sycylijskiej znajduje si?? w bogatym repertuarze debiutowym najwybitniejszych szachist??w ??wiata XX i XXI wieku.Ksi????ka powsta??a z my??l?? o pocz??tkuj??cych szachistach, kt??rzy nie od razu musz?? wiedzie?? wszystko. Analizuje kontynuacje, kt??re, zdaniem autora, s?? najwa??niejsze.W pierwszej cz????ci ksi????ki omawiane s?? poszczeg??lne posuni??cia. W drugiej - zaprezentowane s?? przyk??adowe partie. Ca??o????, dla ??atwiejszego poruszania si?? po poszczeg??lnych wariantach, poprzedzono diagramami.", "Damazy Sowiecki", 2, 40.99, "obrona sycylijska.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "Obrona Skandynawska", "Nie ma ??adnych w??tpliwo??ci, ??e podstaw?? sukcesu w ka??dej partii szachowej jest pomy??lny start.", "Jerzy  Konikowski", 9, 66.99, "obrona skandynawska.jpeg"),
                new Book(BooksCategory.DRAMA.categoryName, "Romeo i Julia", "Chyba nie ma osoby, kt??ra nie s??ysza??aby opowie??ci o p??omiennej, skazanej na kl??sk?? i pot??pienie mi??o??ci kochank??w z Verony. Zanurz si?? w ??wiecie stworzonym przez Williama Szekspira i jeszcze raz odkryj histori?? Romea i Julii. Opowie??ci takie jak ta nigdy si?? nie starzej??. Historia kochank??w pochodz??cych z dw??ch zwa??nionych rod??w, kt??rych potajemne uczucie nieuchronnie prowadzi do tragedii...W swoim najs??ynniejszym dziele William Szekspir wykreowa?? mi??o???? niemal idealn?? ??? zakazan?? i czyst??. Karty ksi????ki wype??niaj?? ciekawe postacie, akcja toczy si?? wartko, a czytelnik dos??ownie przenosi si?? w ??wiat szesnastowiecznych W??och. Dramaty Szekspira to przede wszystkim jednak pi??kny, konsekwentny i barwny styl literacki. Tragedia porusza, obok w??tku romantycznego, tak??e wiele innych, sk??aniaj??cych do refleksji problem??w. Znajdziemy tutaj rozwa??ania na temat mo??liwo??ci zmiany przeznaczenia, walki z Bogiem czy znaczenia ??ycia ludzkiego. To jedna z tych ksi????ek, kt??r?? warto przeczyta?? w ??yciu kilkukrotnie po to, aby za ka??dym razem odkry?? w niej nowe warto??ci i niedostrze??one wcze??niej w??tki. Nowe wydanie dramatu wyr????nia szlachetny papier oraz pi??kna szata graficzna utrzymana w barokowym stylu. Twarda oprawa, kartki szyte mocnymi ni??mi i odporne na zagniecenia nie tylko zachwycaj?? jako??ci??, ale tak??e gwarantuj?? trwa??o???? ksi????ki, kt??ra b??dzie stanowi??a ozdob?? ka??dej biblioteki. Wydanie to z pewno??ci?? zach??ci czytelnik??w, kt??rzy jeszcze nie znaj?? tej wspania??ej lektury, do uzupe??nienia brak??w. Ponadto pozycja ta na pewno b??dzie si?? doskonale prezentowa??a w domowych zbiorach.", "William Shakespeare", 10, 120.55, "romeo.jpeg"),
                new Book(BooksCategory.COOK.categoryName, "Domowe przepisy", "Smacznie, niedrogo i szybko! A przede wszystkim: lekko i zdrowo.Trzymasz w r??ku pierwsz?? ksi????k?? o gotowaniu stworzon?? wsp??lnie przez Ew?? Chodakowsk?? i Tomka Wo??niaka. Ewy przedstawia?? nie trzeba, Tomka mo??esz zna?? z kulinarnego programu telewizyjnego, jaki prowadz?? razem od kilku sezon??w. Poznaj bli??ej ich filozofi?? gotowania, na konkretnych ??? smacznych! ??? przyk??adach. Znajdziesz w tej ksi????ce 37 ulubionych przepis??w Ewy i Tomka na ??niadania, przek??ski, dania mi??sne i wegetaria??skie oraz desery. Przepisy opracowali wsp??lnie ??? Tomek wni??s?? swoja pasj??, kunszt do??wiadczonego kucharza oraz inspiracje, jakie przywi??z?? z podr????y po ??wiecie, a Ewa oraz wsp????pracuj??ca z ni?? specjalistka ??ywienia zadba??y by prezentowane dania by??y lekkie i zdrowe. Wszystkie przygotujesz szybko z dost??pnych wsz??dzie niedrogich sk??adnik??w. Czego chcie?? wi??cej? Chyba tylko towarzystwa bliskich sercu os??b??? Smacznego!i", "Ewa Chodakowska", 3, 26.99, "gotuj z nami.jpeg"),
                new Book(BooksCategory.HISTORY.categoryName, "K??opoty ma??ego liska", "Pi??knie ilustrowana opowiastka o ma??ym lisku Rudasku i jego przyjaci????ce Kasi. Kasia i Grze?? mieszkali na farmie u swoich dziadk??w. Pewnego zimowego poranka Kasia znalaz??a w ??niegu ma??ego liska.", "Wioletta ??wi??ci??ska", 9, 6.99, "lisek.jpeg"),
                new Book(BooksCategory.SCIFI.categoryName, "Solar War", "Explore the final stages of The Horus Heresy in this fantastic series, a must have for all fans! New York Times Bestselling Series. After seven years of bitter war, the end has come at last for the conflict known infamously as the Horus Heresy. Terra now lies within the Warmaster???s sights, the Throneworld and the seat of his father???s rule. Horus??? desire is nothing less than the death of the Emperor of Mankind and the utter subjugation of the Imperium. He has become the ascendant vessel of Chaos, and amassed a terrible army with which to enact his will and vengeance. But the way to the Throne will be hard as the primarch Rogal Dorn, the Praetorian and protector of Terra, marshals the defences. First and foremost, Horus must challenge the might of the Sol System itself and the many fleets and bulwarks arrayed there. To gain even a foothold on Terran soil, he must first contend the Solar War. Thus the first stage of the greatest conflict in the history of all mankind begins.", "John French", 10, 78.99, "solar war.jpeg"),
                new Book(BooksCategory.SCIFI.categoryName, "Diuna", "Arrakis, zwana Diun??, to jedyne we wszech??wiecie ??r??d??o melan??u. Z rozkazu Padyszacha Imperatora planet?? przejmuj?? Atrydzi, zaciekli wrogowie w??adaj??cych ni?? dotychczas Harkonnen??w. Zwyci??stwo ksi??cia Leto Atrydy jest jednak pozorne ??? przej??cie planety ukartowano. W odpowiedzi na atak Imperium i Harkonnen??w dziedzic rodu Atryd??w Paul staje na czele rdzennych mieszka??c??w Diuny i si??ga po imperialny tron. Osza??amiaj??ce po????czenie przygody i mistycyzmu, ekologii i polityki. ???Diuna??? otrzyma??a dwie najbardziej presti??owe nagrody SF: Nebula i Hugo.", "Herbert Frank", 21, 23.46, "diuna.jpeg"),
                new Book(BooksCategory.SCIFI.categoryName, "Wydech", "Wszech??wiat zacz???? si?? jako pot????ny oddech, wstrzymywany w p??ucach??? W tych dziewi??ciu zdumiewaj??co oryginalnych, prowokuj??cych i wzruszaj??cych opowiadaniach Ted Chiang rozwa??a niekt??re z najstarszych pyta?? stawianych przez ludzko????, a tak??e inne, zupe??nie nowe pytania, kt??re mog??y przyj???? na my??l wy????cznie jemu. W opowiadaniu ???Kupiec i wrota alchemika??? czasowy portal zmusza sprzedawc?? tkanin z dawnego Bagdadu do stawiania czo??a starym b????dom, daj??c mu drug?? szans??. W tytu??owym Wydechu obcy uczony dokonuje zdumiewaj??cego odkrycia o uniwersalnych konsekwencjach. W noweli L??k to zawr??t g??owy od wolno??ci mo??liwo???? zajrzenia do alternatywnych wszech??wiat??w umo??liwia bohaterom zupe??nie nowe spojrzenie na kwesti?? wolnej woli", "Chiang Ted", 12, 49.99, "wydech.jpeg"),
                new Book(BooksCategory.ROMANCE.categoryName, "Seks nie mi??o????", "???Seks, nie mi??o??????? to kolejny, pe??en nami??tno??ci romans autorki bestseller??w Vi Keeland. Nat jest po rozwodzie i w??a??nie uk??ada swoje ??ycie na nowo. Chce upora?? si?? z problemami przesz??o??ci i ostatnia rzecz, na jak?? ma teraz ochot??, to nowy zwi??zek. ??ycie pisze dla niej jednak zupe??nie inny scenariusz. Na przyj??ciu weselnym swoich przyjaci???? poznaje Huntera. Ten nieprzeci??tnie przystojny i poci??gaj??cy facet stawia sobie za cel uwiedzenie Nat. Kobieta szybko jednak przejrza??a jego zamiary. W Hunterze widzi klasycznego ??amacza serc, poluj??cego na kobiety. Kiedy nast??pnego ranka Nat budzi si?? z nim w jednym ??????ku, pozostawia mu nieprawdziwy numer telefonu. Przypadkowy seks to nie jest pow??d, dla kt??rego mia??aby zmienia?? swoje plany. Szybko znika z jego ??ycia. Przynajmniej tak jej si?? wydaje. Mija dziewi???? miesi??cy, a kochankowie zn??w si?? spotykaj??. Tym razem Hunter nie pozwoli si?? nabra?? na ten sam numer. Teraz to Hunter ma dla Nat propozycje, kt??rej nie mo??e odrzuci??. Ksi????ka  ???Seks, nie mi??o??????? poch??onie uwag?? czytelniczki lub czytelnika na wiele godzin. Sprawd??, jak?? tajemnic?? skrywa w sobie Hunter i jak potocz?? si?? losy tych bohater??w!", "Keeland Vi", 21, 26.60, "seks nie milosc.jpeg"),
                new Book(BooksCategory.ROMANCE.categoryName, "Hopeless", "Wydawa?? by si?? mog??o, ??e o mi??o??ci napisano ju?? wszystko, wykorzystuj??c te wszystkie s??owa, kt??re przekazuj?? blask i magi?? uczu??. Colleen Hoover, autorka ???Hopeless???, udowodni??a ??wiatu, ??e mo??na zrobi?? to jeszcze lepiej, pi??kniej, bardziej intensywnie. Mo??na dotkn???? prostych pozornie spraw, u??ywaj??c tak niepozornych ??rodk??w, kt??re przesuwaj?? j??dro Ziemi, nadaj??c jej zupe??nie now?? orbit??. Mia??em wra??enie, ??e po ???Morzu spokoju??? - ksi????ce Katji Millay (kt??rej recenzj?? znajdziecie tutaj) bardzo d??ugo nic nie b??dzie w stanie mnie zaskoczy??, poruszy?? czy te?? dotkn????. S??dzi??em, ??e ju?? wtedy nadmiar emocji przela?? si?? przez dusz?? morzem spokoju, dotykaj??c serca. By??o to w sumie nie tak dawno. Historia Sky zaczarowa??a mnie bardziej, ni?? m??g??bym przypuszcza??. Poruszy??a we mnie, dojrza??ym m????czy??nie wszystkie, nawet te najbardziej delikatne, struny. Kr??tkie wprowadzenie zawarte na dw??ch pocz??tkowych stronach powie??ci jawi si?? z pocz??tku jako wyrwane z kontekstu i nie m??wi czytelnikowi zupe??nie nic. W miar?? up??ywu przeczytanych stron staje si?? jednak przejrzystym, a tak??e emocjonalnym, odzwierciedleniem uczuciowej walki Sky. Bior??c do r??ki ksi????k??, jeszcze przed rozpocz??ciem lektury, uwag?? przyci??ga ok??adka, a tak??e wyj??tkowo wymowne jej skrzyde??ka. W trakcie czytania ten kr??tki graficzny przekaz nabiera szczeg??lnego - pod ka??dym wzgl??dem - kszta??tu. Sky i Six, Six i Sky, chwil?? p????niej tak??e Holder i nagle gdzie?? pomi??dzy nimi wy??ania si?? z mrok??w nico??ci Hope. Dwa miesi??ce w trakcie, kt??rych przesun????o si?? dla Sky j??dro Ziemi w osobie Holdera. Wszystko w odpowiednim momencie zaczyna by?? zrozumia??e. Mi??o????, by przetrwa?? zostaje wystawiona na wielk??, ??yciow?? pr??b??, gdy do Sky zaczynaj?? dociera?? przeb??yski jej dzieci??cej ??wiadomo??ci, odsuni??tej lata temu w niebyt nico??ci. Co??, co nigdy nie mia??o i nie powinno mie?? miejsca z si???? tsunami niszczy spokojne, pe??ne mi??o??ci i rado??ci ??ycie Sky. Six tak bardzo potrzebna w tym momencie wspiera j?? przez ocean w wyj??tkowy spos??b, a kochaj??ca matka staje si?? najwi??kszym wrogiem Sky. Trzeba b??dzie wielkiej odwagi, aby ponownie spojrze?? jej w oczy. Wydawca w formie zach??ty do przeczytania ksi????ki zaanonsowa?? niesamowito???? ???Hopeless??? wypowiedziami kilkunastu czytelniczek. Dziwi?? mo??e fakt, ??e nie ma w??r??d nich, cho?? jednego m??skiego spojrzenia. Holder przesuwa do mnie d??o?? i dotyka swoim ma??ym palcem mojego, zupe??nie, jakby nie mia?? si??y chwyci?? mnie za ca???? r??k?? (tak si?? wzruszy??em mnogo??ci?? dozna??, ??e nawet trudno mi przepisa?? ten kr??tki tekst). To mi??e, przedtem trzymali??my si?? ju?? za r??ce, za same palce ??? nigdy. U??wiadamiam sobie, ??e to kolejny pierwszy raz. Wcale nie czuj?? si?? rozczarowana, poniewa?? wiem, ??e w jego wypadku te pierwsze razy nie maj?? znaczenia. Niezale??nie od tego, czy b??dzie mnie ca??owa?? po raz pierwszy, dwudziesty czy milionowy, nic mnie to nie b??dzie obchodzi??, poniewa?? mam pewno????, ??e ju?? i tak do nas nale??y najlepszy pierwszy poca??unek w historii pierwszych poca??unk??w ??? chocia?? nawet si?? nie ca??owali??my. Skoro wszystkie dobre s??owa ju?? pad??y warto zanurzy?? d??onie w urzekaj??cym nurcie tej opowie??ci, s??uchaj??c uwa??nie tego, co ma nam do powiedzenia Sky. A robi to w absolutnie niesamowity spos??b!", "Colleen Hoover", 20, 29.24, "hopeless.jpeg"),
                new Book(BooksCategory.ROMANCE.categoryName, "50 twarzy Greya", "Kiedy studentka literatury Anastasia Steele przychodzi, by przeprowadzi?? wywiad z Christianem Greyem, spotyka m????czyzn?? pi??knego, b??yskotliwego i onie??mielaj??cego. Naiwna i niewinna Ana z przera??eniem u??wiadamia sobie, ??e pragnie tego m????czyzny. Pomimo zagadkowego dystansu, Ana odkrywa, ??e rozpaczliwie chce si?? do niego zbli??y??. Nie potrafi??c oprze?? si?? delikatnej urodzie Any, jej inteligencji i niespokojnemu duchowi, Grey przyznaje, ??e tak??e jej pragnie??? aczkolwiek na w??asnych warunkach. Zaszokowana, cho?? podekscytowana osobliwym gustem erotycznym Greya, Ana si?? waha. Pomimo jego autentycznych sukces??w- mi??dzynarodowych interes??w, ogromnego bogactwa i kochaj??cej rodziny ??? Greya dr??cz?? demony oraz nieposkromiona potrzeba sprawowania kontroli. Kiedy para rozpoczyna fizycznie ??mia??y i nami??tny romans, Ana odkrywa tajemnice Christiana Greya i zg????bia swoje w??asne mroczne ????dze.", "E L James", 100, 18.99, "50 twarzy greya.jpeg"),
                new Book(BooksCategory.COOK.categoryName, "Super proste gotowanie", "Kuchnia prosta, a raczej??? SUPERPROSTA. Oryginalno???? i wspania??e smaki kuchni ??r??dziemnomorskiej zamkni??te w jednej ksi????ce??? Mn??stwo przepis??w, bardzo prostych w wykonaniu i zawieraj??cych nie wi??cej ni?? 6 sk??adnik??w. Od przystawek a?? po s??odko??ci, przez makarony, zupy, drugie dania z mi??sa i ryb, nie wspominaj??c ju?? o pizzy i innych s??onych wypiekach. Superproste gotowanie oznacza u??ywanie niewielu, ale za to w??a??ciwych produkt??w. Oznacza tak??e ograniczanie marnotrawstwa i oszcz??dzanie czasu. Prosta kuchnia to pewna filozofia ??ycia, kt??ra przede wszystkim czyni je lepszym.", "Opracowanie zbiorowe", 3, 40.22, "super proste.jpeg"),
                new Book(BooksCategory.HISTORY.categoryName, "Nasza Wojna 1", "Odkrywcza historia front??w wschodnich I wojny ??wiatowej. Chyba ka??dy s??ysza?? o Sommie, Verdun czy Marnie, miejscach najwa??niejszych bitew frontu zachodniego I wojny ??wiatowej. Niewiele os??b pami??ta jednak o co najmniej r??wnie wa??nych miejscach na mapach frontu wschodniego ??? Gorlicach, Przasnyszu czy Przemy??lu. Ta ksi????ka przywraca pami???? o tych jak??e przecie?? istotnych dla naszych dziej??w wydarzeniach. Nasza wojna. Europa ??rodkowo-Wschodnia 1914???1918. Imperia nie jest typow?? publikacj?? historyczn??. Chocia?? jej autorzy z wielk?? skrupulatno??ci?? przedstawiaj?? dyplomatyczne przyczyny i okoliczno??ci wybuchu I wojny ??wiatowej oraz dok??adnie opisuj?? towarzysz??ce jej dzia??ania militarne, to nie polityka mi??dzynarodowa jest przedmiotem tej ksi????ki. O jej wyj??tkowo??ci stanowi fakt, ??e koncentruje si?? ona przede wszystkim na historiach ludzi, zar??wno jednostek, jak i spo??ecze??stw, i wp??ywie, jaki wywar??y na nich wojenne do??wiadczenia. Dwaj historycy, W??odzimierz Borodziej i Maciej G??rny, wykonali olbrzymi?? prac??, by w przyst??pny spos??b przybli??y?? czytelnikowi ten fragment dziej??w. Ksi????ka zawiera liczne fragmenty list??w i wspomnie?? uczestnik??w dzia??a?? na froncie wschodnim oraz cytaty z dzie?? literackich z okresu wojny, kt??re s??u???? za ilustracj?? ??wczesnego sposobu postrzegania rzeczywisto??ci. Dzi??ki ??ywemu j??zykowi i plastycznym opisom publikacja jest interesuj??ca nie tylko dla specjalist??w.", "G??rny Maciej", 4, 35.99, "nasza wojna1.jpeg"),
                new Book(BooksCategory.HISTORY.categoryName, "Nasza Wojna 2", "Drugi tom fascynuj??cej monografii I wojny ??wiatowej, kt??ra otworzy??a Polsce drog?? do odzyskania niepodleg??o??ci w 1918 roku. Wielk?? Wojn?? na wschodzie Europy wywo??a??y imperia, bi??y si?? w niej narody a gin??li zwykli ludzie. Im d??u??ej trwa??a, tym trudniej by??o wyobrazi?? sobie, jak si?? sko??czy i czy w og??le kiedy?? nastanie pok??j. Tego, ??e zmianie ulegnie prawie wszystko, nie przewidzia?? nikt. Drugi tom Naszej wojny opisuje t?? wielk?? zmian?? na wielu polach. Opowiada o nowej taktyce prowadzenia dzia??a?? zbrojnych, o strajkach i buntach robotniczych, prawach kobiet, kulturze, polityce i walce o niepodleg??o???? narod??w Europy ??rodkowo-Wschodniej i Ba??kan??w. Wszystko to w porywaj??cym stylu - przyst??pnie, czasem dowcipnie, zawsze w oparciu o ??r??d??a, w tym dotychczas rzadko wykorzystywane.", "G??rny Maciej", 4, 37.99, "nasza wojna2.jpeg"),
                new Book(BooksCategory.DRAMA.categoryName, "Za ??adne skarby", "??ycie ma z regu??y inne plany ni?? my. Ewa spe??ni??a swoje marzenie ??? wyrwa??a si?? z ma??ej mazurskiej wsi, sko??czy??a mikrobiologi??. W??a??nie wybiera si?? na sta?? naukowy do Pary??a. Ma te?? ca??kiem sensownego faceta. I nagle wszystko si?? ko??czy. Jeden telefon uruchamia bieg zdarze??, jakich Ewa nigdy by si?? nie spodziewa??a. Dziewczyna wraca do za??ciankowej rzeczywisto??ci, do pij??cego ojca i chorego brata. Przed ni?? wiele problem??w, ale te?? mi??o???? do utraty tchu. Co zrobi Ewa? Jedno jest pewne: za ??adne skarby si?? nie podda! Si??gnij po ksi????k??, w kt??rej znajdziesz kobiec?? si???? i odwa??n?? nami??tno????, dramatyczne decyzje i nieczyste interesy, zabawn??, ale i nielukrowan?? stron?? ??ycia. Za ??adne skarby to powie????, jakiej jeszcze nie by??o. Vera Falski to kobieta z krwi i ko??ci. Znasz j??. Mo??e by?? twoj?? s??siadk??. Przygl??da si?? sobie i innym kobietom z polskich ulic, uliczek i ??cie??ek. Z jej obserwacji, refleksji i marze?? powsta??a ta ksi????ka. Vera Falski to tysi??ce kobiet zakl??tych w jednym pi??rze. Mo??e twoja historia b??dzie inspiracj?? do jej nast??pnej powie??ci???", "Falski Vera", 8, 64.77, "za zadne skarby.jpeg"),
                new Book(BooksCategory.DRAMA.categoryName, "Mi??o???? przychodzi z deszczem", "Porywaj??ca opowie???? o wi??zach krwi i serc. Marek i Marcin, jak to bli??niacy, byli nieroz????czni. Do czasu. Marek rzuca prac?? i wyje??d??a bez s??owa. Drugi z braci milczy. Po kilku miesi??cach Marek decyduje si?? wr??ci??. Przyje??d??a z Ani??, dziewczyn??, kt??r?? uwa??a za t?? jedn?? jedyn??, wymarzon??. Jest przera??ony, gdy odkrywa, ??e jego bratowa jest w ci????y. Nim zd????y podj???? decyzj??, co zrobi??, ginie w wypadku. Kilka lat po tym tragicznym wydarzeniu Ania wraca do Poznania i spotyka drugiego z braci. Tylko czy na pewno?", "Mila Rudnik", 11, 19.69, "milosc przychodzi z deszczem.jpeg"),
                new Book(BooksCategory.DRAMA.categoryName, "We dwoje", "Pe??na pogody ducha Karolina Tyrolska od zawsze wierzy??a, ??e los jej sprzyja. Mia??a kochaj??cego m????a, satysfakcjonuj??c?? prac??, stabilizacj?? finansow??, wi??c mimo problem??w z zaj??ciem w ci??????, by??a pewna, ??e pr??dzej czy p????niej uda si?? zrealizowa?? jej marzenie. Pewien lipcowy dzie?? wystawi?? j?? na niespodziewan?? pr??b??. Po raz pierwszy ??ycie przestaje uk??ada?? si?? po jej my??li i Karolina czuje, ??e traci grunt pod nogami. W jej ??wiat wkraczaj?? nowe postacie, inne schodz?? na drugi plan. Zagubiona i przera??ona pozwala, by bliscy decydowali za ni??. A ka??dy ma inny spos??b na wybawienie jej z opresji???Tylko czy to jeszcze jest jej ??ycie?", "Wo??niczka Katarzyna", 10, 27.50, "we dwoje.jpeg")
        };

        Arrays.stream(books)
                .forEach(bookRepository::save);

    }
}


