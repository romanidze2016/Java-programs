import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NBCFS {

	ArrayList<CFSData> data = new ArrayList<CFSData>();

	float[] yesMeans = new float[5];
	float[] noMeans = new float[5];

	float[] yesSDs = new float[5];
	float[] noSDs = new float[5];

	int yesCounter = 0;
	int noCounter = 0;

	public NBCFS(String training) {
		// test print out the training data
		try (BufferedReader br = new BufferedReader(new FileReader(training))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				String[] p = sCurrentLine.split(",");
				CFSData d = new CFSData(p[0], p[1], p[2], p[3], p[4],p[5]);

				data.add(d);

				// calculate no and yes sums of values for each attribute
				if (p[5].equals("yes")) {

					for (int i = 0; i < 5; i++) {
						yesMeans[i] += Float.parseFloat(p[i]);
					}
					yesCounter++;
				} else {
					System.out.println("HES PICKING NO");

					for (int i = 0; i < 5; i++) {
						noMeans[i] += Float.parseFloat(p[i]);
					}
					noCounter++;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// calculate means
		for (int i = 0; i < 5; i++) {
			yesMeans[i] = yesMeans[i] / yesCounter;
			noMeans[i] = noMeans[i] / noCounter;
		}

		calculateSDs();
	}

	public NBCFS(ArrayList<String> training) {
		for (String sCurrentLine : training) {
			String[] p = sCurrentLine.split(",");

			CFSData d = new CFSData(p[0], p[1], p[2], p[3], p[4],p[5]);
			data.add(d);

			// calculate no and yes sums of values for each attribute
			if (p[5].equals("yes")) {
				for (int i = 0; i < 5; i++) {
					yesMeans[i] += Float.parseFloat(p[i]);
				}
				yesCounter++;
			} else {
				for (int i = 0; i < 5; i++) {
					noMeans[i] += Float.parseFloat(p[i]);
				}

				noCounter++;
			}
		}

		// calculate means
		for (int i = 0; i < 5; i++) {
			yesMeans[i] = yesMeans[i] / yesCounter;
			noMeans[i] = noMeans[i] / noCounter;
		}

		calculateSDs();
	}

	public void calculateSDs() {
		// calculate the upper part of the sd formula -> (Xi - u)^2
		for (int i = 0; i < data.size(); i++) {
			CFSData currentData = data.get(i);

			if (currentData.getStatus()) {
				for (int j = 0; j < 5; j++) {
					yesSDs[j] += Math.pow(currentData.getAttributeAt(j) - yesMeans[j], 2);
				}
			} else {
				for (int j = 0; j < 5; j++) {
					noSDs[j] += Math.pow(currentData.getAttributeAt(j) - noMeans[j], 2);
				}
			}
		}

		// calculate final sd values for each attribute
		for (int i = 0; i < 5; i++) {
			yesSDs[i] = (float) Math.sqrt(yesSDs[i] / (yesCounter - 1));
			noSDs[i] = (float) Math.sqrt(noSDs[i] / (noCounter - 1));
		}
	}

	public void classifyData(String testing) {
		try (BufferedReader br = new BufferedReader(new FileReader(testing))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				String[] p = sCurrentLine.split(",");

				CFSData d = new CFSData(p[0], p[1], p[2], p[3], p[4],"");

				// calculate P(Ei|yes) and P(Ei|no) for each attribute
				float[] attributeProbsY = new float[5];
				float[] attributeProbsN = new float[5];
				for (int i = 0; i < 5; i++) {
					float firstPart = (float) (1.0 / (yesSDs[i] * Math.sqrt(2 * Math.PI)));
					float secondPart = (float) Math
							.exp(-Math.pow(d.getAttributeAt(i) - yesMeans[i], 2) / (2 * yesSDs[i] * yesSDs[i]));
					attributeProbsY[i] = firstPart * secondPart;

					firstPart = (float) (1.0 / (noSDs[i] * Math.sqrt(2 * Math.PI)));
					secondPart = (float) Math
							.exp(-Math.pow(d.getAttributeAt(i) - noMeans[i], 2) / (2 * noSDs[i] * noSDs[i]));
					attributeProbsN[i] = firstPart * secondPart;
				}

				// calculate overall P(yes|E) and P(no|E) for the given example
				float yesProbability = 1;
				float noProbability = 1;
				for (int i = 0; i < 5; i++) {
					yesProbability *= attributeProbsY[i];
					noProbability *= attributeProbsN[i];
				}
				yesProbability *= ((float) yesCounter) / ((float) data.size());
				noProbability *= ((float) noCounter) / ((float) data.size());

				if (noProbability > yesProbability) {
					System.out.println("no");
				} else {
					System.out.println("yes");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> classifyData(ArrayList<String> testing) {
		ArrayList<String> result = new ArrayList<String>();
		
		for (String sCurrentLine : testing) {
			String[] p = sCurrentLine.split(",");

			CFSData d = new CFSData(p[0], p[1], p[2], p[3], p[4],"");

			// calculate P(Ei|yes) and P(Ei|no) for each attribute
			float[] attributeProbsY = new float[5];
			float[] attributeProbsN = new float[5];
			for (int i = 0; i < 5; i++) {
				float firstPart = (float) (1.0 / (yesSDs[i] * Math.sqrt(2 * Math.PI)));
				float secondPart = (float) Math
						.exp(-Math.pow(d.getAttributeAt(i) - yesMeans[i], 2) / (2 * yesSDs[i] * yesSDs[i]));
				attributeProbsY[i] = firstPart * secondPart;

				firstPart = (float) (1.0 / (noSDs[i] * Math.sqrt(2 * Math.PI)));
				secondPart = (float) Math
						.exp(-Math.pow(d.getAttributeAt(i) - noMeans[i], 2) / (2 * noSDs[i] * noSDs[i]));
				attributeProbsN[i] = firstPart * secondPart;
			}

			// calculate overall P(yes|E) and P(no|E) for the given example
			float yesProbability = 1;
			float noProbability = 1;
			for (int i = 0; i < 5; i++) {
				yesProbability *= attributeProbsY[i];
				noProbability *= attributeProbsN[i];
			}
			yesProbability *= ((float) yesCounter) / ((float) data.size());
			noProbability *= ((float) noCounter) / ((float) data.size());

			if (noProbability > yesProbability) {
				result.add("no");
			} else {
				result.add("yes");
			}
		}
		
		return result;
	}

	public void printMeans() {
		System.out.println("Yes means");
		for (int i = 0; i < 5; i++) {
			System.out.print(yesMeans[i] + "  ");
		}
		System.out.println();
		System.out.println("No means");
		for (int i = 0; i < 5; i++) {
			System.out.print(noMeans[i] + "  ");
		}
		System.out.println();
	}

	public void printSDs() {
		System.out.println("Yes SDs");
		for (int i = 0; i < 5; i++) {
			System.out.print(yesSDs[i] + "  ");
		}
		System.out.println();
		System.out.println("No SDs");
		for (int i = 0; i < 5; i++) {
			System.out.print(noSDs[i] + "  ");
		}
		System.out.println();
	}
}
