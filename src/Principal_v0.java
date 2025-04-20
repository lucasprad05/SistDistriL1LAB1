
//--------------------------------------------
/**
 * Laboratorio 2 de Sistemas Distribuidos
 * Sistemas Distribuídos - SDCO8A- 2025/1
 * Professor: Lucio Agostinho  Rocha
 *
 * Ana Carolina Ribeiro Miranda - 2208407
 * Lucas Castilho Pinto Prado - 2367980
 */
//--------------------------------------------

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Principal_v0 {

	public final static Path path = Paths
			.get("src/fortune-br.txt");
	private int NUM_FORTUNES = 0;

	public class FileReader {

		public int countFortunes() throws FileNotFoundException {

			int lineCount = 0;

			InputStream is = new BufferedInputStream(new FileInputStream(
					path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					is))) {

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();

				}// fim while

				//System.out.println(lineCount);
			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
			return lineCount;
		}

		public void parser(HashMap<Integer, String> hm)
				throws FileNotFoundException {

			InputStream is = new BufferedInputStream(new FileInputStream(
					path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					is))) {

				int lineCount = 0;

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();
					StringBuffer fortune = new StringBuffer();
					while (!(line == null) && !line.equals("%")) {
						fortune.append(line + "\n");
						line = br.readLine();
						// System.out.print(lineCount + ".");
					}

					hm.put(lineCount, fortune.toString());
					//System.out.println(fortune.toString());

					//System.out.println(lineCount);
				}// fim while

			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
		}

		public void read(HashMap<Integer, String> hm)
				throws FileNotFoundException {

			if (hm.isEmpty()) {
				System.out.println("Nenhuma fortuna carregada.");
				return;
			}
			SecureRandom rand = new SecureRandom();
			int key = rand.nextInt(hm.size()) + 1; //+1, pois o parser incrementa o lineCount no comeco
			System.out.println("\nFortuna aleatória:");
			System.out.println(hm.get(key));
		}

		public void write(HashMap<Integer, String> hm)
				throws FileNotFoundException {

			Scanner scanner = new Scanner(System.in);
			System.out.println("Digite uma fortuna (digite uma linha apenas e digite '%' para encerrar):");
			StringBuilder novaFortuna = new StringBuilder();
			String line;
			while (true) {
				line = scanner.nextLine();
				if (line.trim().equals("%")) {
					break;
				}
				novaFortuna.append(line).append("\n");
			}
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toString(), true))) {
				bw.write(novaFortuna.toString());
				bw.write("%\n");
				System.out.println("Fortuna adicionada com sucesso!");
			} catch (IOException e) {
				System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
			}
		}
	}

	public void iniciar() {

		FileReader fr = new FileReader();
		try {
			NUM_FORTUNES = fr.countFortunes();
			HashMap hm = new HashMap<Integer, String>();
			fr.parser(hm);
			fr.read(hm);
			fr.write(hm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Principal_v0().iniciar();
	}

}
