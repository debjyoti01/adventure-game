package adventure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CSVRead {
	// this method read the csv file and return the integer matrix
	public static int[][] read(String csvFile) {
		String filePath = csvFile;
		List<List<Integer>> data = new ArrayList<>();

		try {
			String absoluttefilePath = new File("").getAbsolutePath();
			BufferedReader br = new BufferedReader(new FileReader(absoluttefilePath + filePath));
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				List<Integer> row = new ArrayList<>();
				while (st.hasMoreTokens()) {
					String token = st.nextToken();
					int value = Integer.parseInt(token);
					row.add(value);
				}
				data.add(row);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int[][] matrix = new int[data.size()][];
		for (int i = 0; i < data.size(); i++) {
			List<Integer> row = data.get(i);
			matrix[i] = new int[row.size()];
			for (int j = 0; j < row.size(); j++) {
				matrix[i][j] = row.get(j);
			}
		}
		return matrix;
	}
}
