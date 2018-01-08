package euroexam;

/*
 readMessage visszadobja az aktuális üzenetet, ha nincs net vagy bármi akkor null-t ad vissza
 */
import euroexam.spec.MessageReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EuroexamMessageReader implements MessageReader {

    private final String DATE_FILTER;

    public EuroexamMessageReader(String dateFilter) {
        this.DATE_FILTER = dateFilter;
    }

    @Override
    public String readMessage() {
        String keresetSzoveg = null;
        try {
            //Html oldal lekérése majd csak a <body> tag kiíratása
            Document doc = Jsoup.connect("http://eredmeny.itk.hu/default.asp?id=20170100120001XQRU").get();
            Elements elements = doc.select("body"); //<body> tag

            for (Element element : elements) {
                //soronként végig megy és ellenőrzi, hogy az átadot
                //"március 22" string benne van-e az adot sorban
                System.out.println(element.text());
                if (element.text().contains(DATE_FILTER)) {
                    keresetSzoveg = element.text();

                }

            }
        } catch (Exception e) {
            System.out.println("Hiba: " + e.getMessage());
        }
        return keresetSzoveg;
    }
}
