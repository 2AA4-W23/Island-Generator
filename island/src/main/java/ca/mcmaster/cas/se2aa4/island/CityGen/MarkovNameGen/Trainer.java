package ca.mcmaster.cas.se2aa4.island.CityGen.MarkovNameGen;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
    private static int[][] letters = new int[26][26];
    private static int[] lengths;
	static int[] rowSum = new int[27];

    public static void main(String[] args){
        train();
    }

    public static void train(){
        List<String> data = getData();
        for(int x = 0; x < letters.length; x++) {
			for(int y = 0; y < letters.length; y++) {
				letters[x][y] = 0;
			}
		}
        for(int i = 0; i < data.size(); i++) {
            for(int j = 1; j < data.get(i).length(); j++){
                int idx1 = data.get(i).charAt(j-1) -97; 
                int idx2 = data.get(i).charAt(j) -97;
                letters[idx1][idx2] += 1;
            }
        }

        lengths = new int[data.size()];
        for(int x = 0; x < lengths.length; x++) {
			lengths[x] = 0;
		}
        for(int i = 0; i < data.size(); i++){
            lengths[data.get(i).length()]++; 
        }

        for(int i = 0; i < 26; i++) {
			int sum = 0;
			for(int j = 0; j < 26; j++) {
				sum += letters[i][j];
				letters[i][j] = sum;
			}
			rowSum[i] = sum;
		}
        print();
    }

    public static List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("tokyo");
        data.add("beijing");
        data.add("shanghai");
        data.add("delhi");
        data.add("mumbai");
        data.add("guangzhou");
        data.add("osaka");
        data.add("kolkata");
        data.add("dhaka");
        data.add("cairo");
        data.add("moscow");
        data.add("bangkok");
        data.add("karachi");
        data.add("istanbul");
        data.add("paris");
        data.add("nagoya");
        data.add("seoul");
        data.add("lagos");
        data.add("jakarta");
        data.add("hyderabad");
        data.add("kinshasa");
        data.add("lima");
        data.add("bengaluru");
        data.add("tianjin");
        data.add("london");
        data.add("shenzhen");
        data.add("tehran");
        data.add("bangalore");
        data.add("chicago");
        data.add("ahmedabad");
        data.add("shenyang");
        data.add("baghdad");
        data.add("chennai");
        data.add("toronto");
        data.add("hangzhou");
        data.add("surat");
        data.add("chengdu");
        data.add("yangon");
        data.add("wuhan");
        data.add("pune");
        data.add("madrid");
        data.add("dallas");
        data.add("singapore");
        data.add("phoenix");
        data.add("kabul");
        data.add("suzhou");
        data.add("hyderabad");
        data.add("riyadh");
        data.add("rabat");
        data.add("rajpur");
        data.add("riyadh");
        data.add("rio");
        data.add("rosario");
        data.add("riverside");
        data.add("ankara");
        data.add("barcelona");
        data.add("harbin");
        data.add("qingdao");
        data.add("quebec");
        data.add("quetta");
        data.add("queens");
        data.add("quincy");
        data.add("quibdo");
        data.add("guadalajara");
        data.add("melbourne");
        data.add("bogota");
        data.add("kunming");
        data.add("yokohama");
        data.add("busan");
        data.add("hanoi");
        data.add("taipei");
        data.add("casablanca");
        data.add("incheon");
        data.add("bangalore");
        data.add("nairobi");
        data.add("chongqing");
        data.add("vienna");
        data.add("valentia");
        data.add("virginia");
        data.add("venezuela");
        data.add("vancouver");
        data.add("vladmir");
        data.add("vaughan");
        data.add("durban");
        data.add("bangkok");
        data.add("lahore");
        data.add("kanpur");
        data.add("alexandria");
        data.add("giza");
        data.add("zhengzhou");
        data.add("changsha");
        data.add("guangzhou");
        data.add("jeddah");
        data.add("santiago");
        data.add("berlin");
        data.add("frankfurt");
        data.add("accra");
        data.add("jinan");
        data.add("hefei");
        data.add("dalian");
        data.add("abidjan");
        data.add("algiers");
        data.add("surabaya");
        data.add("jinan");
        data.add("dakar");
        data.add("hamburg");
        data.add("yaounde");
        data.add("sydney");
        data.add("kiev");
        data.add("pyongyang");
        data.add("johannesburg");
        data.add("montreal");
        data.add("monterrey");
        data.add("xian");
        data.add("amsterdam");
        data.add("madrid");
        data.add("rome");
        data.add("shantou");
        data.add("kampala");
        data.add("khartoum");
        data.add("moscow");
        data.add("abuja");
        data.add("taichung");
        data.add("ibadan");
        data.add("manila");
        data.add("jakarta");
        data.add("doha");
        data.add("brussels");
        data.add("dakar");
        data.add("kano");
        data.add("luzhou");
        data.add("kaduna");
        data.add("naples");
        data.add("athens");
        data.add("lisbon");
        data.add("lusaka");
        data.add("kunming");
        data.add("kabul");
        data.add("kinshasa");
        data.add("afghanistan");
        data.add("albania");
        data.add("algeria");
        data.add("andorra");
        data.add("angola");
        data.add("antigua");
        data.add("argentina");
        data.add("armenia");
        data.add("australia");
        data.add("austria");
        data.add("azerbaijan");
        data.add("bahamas");
        data.add("bahrain");
        data.add("bangladesh");
        data.add("barbados");
        data.add("belarus");
        data.add("belgium");
        data.add("belize");
        data.add("benin");
        data.add("bhutan");
        data.add("braintree");
        data.add("bluewater");
        data.add("bolivia");
        data.add("bloomington");
        data.add("bosnia");
        data.add("botswana");
        data.add("brazil");
        data.add("brunei");
        data.add("bulgaria");
        data.add("burkina");
        data.add("burundi");
        data.add("cabo");
        data.add("cambodia");
        data.add("cameroon");
        data.add("canada");
        data.add("central");
        data.add("chad");
        data.add("chile");
        data.add("china");
        data.add("colombia");
        data.add("comoros");
        data.add("congo");
        data.add("costa");
        data.add("cote");
        data.add("croatia");
        data.add("cuba");
        data.add("uzbekistan");
        data.add("ukraine");
        data.add("udine");
        data.add("union");
        data.add("uptown");
        data.add("cyprus");
        data.add("czech");
        data.add("denmark");
        data.add("djibouti");
        data.add("dominica");
        data.add("dominican");
        data.add("ecuador");
        data.add("egypt");
        data.add("equatorial");
        data.add("eritrea");
        data.add("estonia");
        data.add("eswatini");
        data.add("ethiopia");
        data.add("fiji");
        data.add("finland");
        data.add("france");
        data.add("frankfurt");
        data.add("finland");
        data.add("fairfield");
        data.add("gabon");
        data.add("gambia");
        data.add("georgia");
        data.add("germany");
        data.add("ghana");
        data.add("greece");
        data.add("grenada");
        data.add("guatemala");
        data.add("guinea");
        data.add("guyana");
        data.add("haiti");
        data.add("honduras");
        data.add("hungary");
        data.add("heathrow");
        data.add("hannover");
        data.add("hanau");
        data.add("iceland");
        data.add("india");
        data.add("indonesia");
        data.add("iran");
        data.add("iraq");
        data.add("ireland");
        data.add("israel");
        data.add("italy");
        data.add("jamaica");
        data.add("japan");
        data.add("jordan");
        data.add("jakarta");
        data.add("jackson");
        data.add("jersey");
        data.add("kazakhstan");
        data.add("kenya");
        data.add("kiribati");
        data.add("kosovo");
        data.add("kuwait");
        data.add("kyrgyzstan");
        data.add("lagos");
        data.add("latvia");
        data.add("lebanon");
        data.add("lesotho");
        data.add("liberia");
        data.add("libya");
        data.add("liechtenstein");
        data.add("lithuania");
        data.add("luxembourg");
        data.add("madagascar");
        data.add("malawi");
        data.add("malaysia");
        data.add("maldives");
        data.add("mali");
        data.add("malta");
        data.add("marshall");
        data.add("mauritania");
        data.add("mauritius");
        data.add("mexico");
        data.add("micronesia");
        data.add("moldova");
        data.add("monaco");
        data.add("mongolia");
        data.add("montenegro");
        data.add("morocco");
        data.add("mozambique");
        data.add("myanmar");
        data.add("namibia");
        data.add("nauru");
        data.add("nipissing");
        data.add("nanaimo");
        data.add("nepal");
        data.add("nagasaki");
        data.add("osaka");
        data.add("oakland");
        data.add("oklahoma");
        data.add("oran");
        data.add("omaha");
        data.add("oman");
        data.add("okinawa");
        data.add("xing");
        data.add("xenia");
        data.add("xenophon");
        data.add("xinzhou");
        data.add("xangongo");
        data.add("xerta");
        data.add("zanzibar");
        data.add("zabid");
        data.add("zayed");
        data.add("zile");
        data.add("zurich");
        data.add("zamora");
        data.add("york");
        data.add("yorkshire");
        data.add("yokohama");
        data.add("yala");
        data.add("yantai");
        data.add("yakima");
        data.add("yanbu");
        data.add("wuhan");
        data.add("warsaw");
        data.add("washington");
        data.add("winnipeg");
        data.add("windsor");
        data.add("woodlands");
        data.add("westminster");
        return data;
    }

    private static void print(){
        System.out.print("{");
        for(int x = 0; x < letters.length; x++) {
            System.out.print("{");
			for(int y = 0; y < letters.length; y++) {
				System.out.print(letters[x][y]);
                if(y != letters.length - 1) System.out.print(",");
			}
			System.out.println("},");
		}
        System.out.println("}");
        System.out.println("{");
        for(int x = 0; x < rowSum.length; x++) {
            System.out.print(rowSum[x]);
            if(x != letters.length - 1) System.out.print(",");
		}
        System.out.println("}");
    }
}
