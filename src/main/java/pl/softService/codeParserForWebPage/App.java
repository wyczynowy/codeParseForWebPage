package pl.softService.codeParserForWebPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
	static int maxLineLength = 80;

	static BufferedReader fileReader;
	private static StringBuilder inputFilePart;
	private static StringBuilder outputFilePart;
	static FileWriter fileWriter;

	public static void main(String[] args) {
		File folder = new File("./");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.getName().indexOf(".txt") > 0) {

				int tabValue = 0;

				inputFilePart = new StringBuilder();
				outputFilePart = new StringBuilder();

				/* ODCZYT DANYCH Z PLIKU */
				try {
					fileReader = new BufferedReader(new FileReader(file.getName()));
					String readedLine = "";
					while ((readedLine = fileReader.readLine()) != null) {
						inputFilePart.append(readedLine + "\r\n");
						tabValue = 0;
						for (char sign : readedLine.toCharArray()) {
							if (sign == '\t') {
								tabValue++;
							} else {
								break;
							}
						}

						if (tabValue > 0) {
							readedLine = readedLine.replaceAll("<", "&lt;");
							readedLine = readedLine.replaceAll(">", "&gt;");
							outputFilePart.append("<span class='tabbed" + tabValue + "'>" + (readedLine.length() > maxLineLength ? readedLine.substring(tabValue, maxLineLength + 1) : readedLine.substring(tabValue)) + "</span><br>\r\n");
							if (readedLine.length() > maxLineLength + 1) {
								outputFilePart.append("<span class='tabbed" + (tabValue + 1) + "'>" + readedLine.substring(maxLineLength + 1) + "</span><br>\r\n");
							}
						} else {
							readedLine = readedLine.replaceAll("<", "&lt;");
							readedLine = readedLine.replaceAll(">", "&gt;");
							outputFilePart.append((readedLine.length() > maxLineLength ? readedLine.substring(tabValue, maxLineLength + 1) : readedLine.substring(tabValue)) + "<br>\r\n");
							if (readedLine.length() > maxLineLength + 1) {
								outputFilePart.append("<span class='tabbed" + (tabValue + 1) + "'>" + readedLine.substring(maxLineLength + 1) + "</span><br>\r\n");
							}
						}
					}
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (fileReader != null) {
						try {
							fileReader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				/* ********** */

				/* ZAPIS DANYCH DO PLIKU */
				try {

					fileWriter = new FileWriter(file.getName());
					fileWriter.write(inputFilePart.toString());
					fileWriter.write("\r\n\r\n============================== Dane po konwersji: ==============================\r\r\r\r");
					fileWriter.write(outputFilePart.toString());
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (fileWriter != null) {
						try {
							fileWriter.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				System.out.println("\r\n POMYŚLNIE ZAKOŃCZONO KONWERSJĘ PLIKU \"" + file.getName() + "\"");

			}
		}
	}
}
