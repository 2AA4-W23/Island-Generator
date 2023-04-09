package ca.mcmaster.cas.se2aa4.island.CityGen.MarkovNameGen;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
    private int[][] letters = new int[27][27];
    private int[] lengths = new int[30];
	static int[] rowSum = new int[27];


    public void train(){
        List<String> data = getData();
        for(int i = 0; i < data.size(); i++) {
            for(int j = 1; j < data.get(i).length(); j++){
                int idx1 = data.get(i).charAt(j-1)-97; 
                int idx2 = data.get(i).charAt(j)-97;
                if(idx1 < 0) idx1 += 32;
                if(idx2 < 0) idx2 += 32;
                letters[idx1][idx2] += 1;
            }
        }

        lengths = new int[data.size()];
        for(int i = 0; i < data.size(); i++){
            lengths[data.get(i).length()]++; 
        }

        for(int i = 0; i < letters.length; i++) {
			int sum = 0;
			for(int j = 0; j < letters.length; j++) {
				sum += letters[i][j];
				letters[i][j] = sum;
			}
			rowSum[i] = sum;
		}
        print();
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("Tokyo");
        data.add("Beijing");
        data.add("Shanghai");
        data.add("Delhi");
        data.add("Mumbai");
        data.add("Guangzhou");
        data.add("Osaka");
        data.add("Kolkata");
        data.add("Dhaka");
        data.add("Cairo");
        data.add("Moscow");
        data.add("Bangkok");
        data.add("Karachi");
        data.add("Istanbul");
        data.add("Paris");
        data.add("Nagoya");
        data.add("Seoul");
        data.add("Lagos");
        data.add("Jakarta");
        data.add("Hyderabad");
        data.add("Kinshasa");
        data.add("Lima");
        data.add("Bengaluru");
        data.add("Tianjin");
        data.add("London");
        data.add("Shenzhen");
        data.add("Tehran");
        data.add("Bangalore");
        data.add("Chicago");
        data.add("Ahmedabad");
        data.add("Shenyang");
        data.add("Baghdad");
        data.add("Chennai");
        data.add("Toronto");
        data.add("Hangzhou");
        data.add("Surat");
        data.add("Chengdu");
        data.add("Yangon");
        data.add("Johannesburg");
        data.add("Wuhan");
        data.add("Pune");
        data.add("Madrid");
        data.add("Dallas");
        data.add("Singapore");
        data.add("Phoenix");
        data.add("Kabul");
        data.add("Suzhou");
        data.add("Hyderabad");
        data.add("Riyadh");
        data.add("Ankara");
        data.add("Barcelona");
        data.add("Harbin");
        data.add("Qingdao");
        data.add("Guadalajara");
        data.add("Melbourne");
        data.add("Bogota");
        data.add("Kunming");
        data.add("Yokohama");
        data.add("Busan");
        data.add("Hanoi");
        data.add("Taipei");
        data.add("Casablanca");
        data.add("Incheon");
        data.add("Bangalore");
        data.add("Nairobi");
        data.add("Chongqing");
        data.add("Vienna");
        data.add("Durban");
        data.add("Bangkok");
        data.add("Lahore");
        data.add("Kanpur");
        data.add("Alexandria");
        data.add("Giza");
        data.add("Zhengzhou");
        data.add("Changsha");
        data.add("Guangzhou");
        data.add("Jeddah");
        data.add("Santiago");
        data.add("Berlin");
        data.add("Frankfurt");
        data.add("Accra");
        data.add("Jinan");
        data.add("Hefei");
        data.add("Dalian");
        data.add("Abidjan");
        data.add("Algiers");
        data.add("Surabaya");
        data.add("Jinan");
        data.add("Dakar");
        data.add("Hamburg");
        data.add("Yaounde");
        data.add("Sydney");
        data.add("Kiev");
        data.add("Pyongyang");
        data.add("Johannesburg");
        data.add("Montreal");
        data.add("Monterrey");
        data.add("Xian");
        data.add("Amsterdam");
        data.add("Madrid");
        data.add("Rome");
        data.add("Shantou");
        data.add("Kampala");
        data.add("Shijiazhuang");
        data.add("Khartoum");
        data.add("Moscow");
        data.add("Abuja");
        data.add("Taichung");
        data.add("Ibadan");
        data.add("Manila");
        data.add("Jakarta");
        data.add("Doha");
        data.add("Brussels");
        data.add("Dakar");
        data.add("Kano");
        data.add("Luzhou");
        data.add("Kaduna");
        data.add("Naples");
        data.add("Athens");
        data.add("Lisbon");
        data.add("Lusaka");
        data.add("Kunming");
        data.add("Kabul");
        data.add("Kinshasa");
        return data;
    }

    private void print(){
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
