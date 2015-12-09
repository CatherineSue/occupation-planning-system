package jsonPick;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class main {
	public static void main(String[] args) {
		BufferedReader reader = null;
		int i = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			String formatBirthday;
			String formatStart;
			String formatEnd;
			String formatrefreshAt;
			String formatCreateAt;
			String formatUpdateAt;
			FileInputStream fileInputStream = new FileInputStream("/Users/mac/WORK/大创/51lietou/out.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {

				tempString = tempString.replace("#RESUMEID#","[");
				tempString += "]";
				JSONArray jsonArray = JSONArray.fromObject(tempString);

				tempString = "";
				JSONObject jsonObject = jsonArray.getJSONObject(0);

				System.out.println("[" + i + "]address="
						+ jsonObject.get("address")); // address
				System.out.println("[" + i + "]age=" + jsonObject.get("age")); // age

				String jsonstamp_s = jsonObject.getString("birthday");
				long jsonstamp_l = Long.valueOf(jsonstamp_s);
				Date dt = new Date(jsonstamp_l);
				formatBirthday = sdf.format(dt);
				System.out.println("[" + i + "]birthday=" + formatBirthday); // birthday
				jsonstamp_s = jsonObject.getString("createAt");
				jsonstamp_l = Long.valueOf(jsonstamp_s);
				dt = new Date(jsonstamp_l);
				formatCreateAt = sdf.format(dt);
				System.out.println("[" + i + "]createAt=" + formatCreateAt);
				System.out.println("[" + i + "]degree ="
						+ jsonObject.get("degree")); // degree
				System.out.println("[" + i + "]degree ="
						+ jsonObject.get("description")); // description
				System.out.println("[" + i + "]expectPosition ="
						+ jsonObject.get("expectPosition"));// expectPosition
				System.out.println("[" + i + "]expectSalay ="
						+ jsonObject.get("expectSalary")); // expectSalary
				System.out.println("[" + i + "]gender="
						+ jsonObject.get("gender")); // gender
				System.out.println("[" + i + "]id=" + jsonObject.get("id")); // id
				System.out.println("[" + i + "]isUploadFlag="
						+ jsonObject.get("isUploadFlag")); // isUploadFlag
				System.out.println("[" + i + "]married="
						+ jsonObject.get("married")); // married
				System.out.println("[" + i + "]nowJobStatus="
						+ jsonObject.get("nowJobStatus")); // nowJobStatus
				if (jsonObject.containsKey("projects")) {
					System.out.println("[" + i + "]projects:");
					tempString = jsonObject.get("projects").toString();
					JSONArray jsonArray_projects = JSONArray
							.fromObject(tempString);
					int size_projects = jsonArray_projects.size();

					for (int j = 0; j < size_projects; j++) {
						JSONObject jsonObject_projects = jsonArray_projects
								.getJSONObject(j);
						jsonstamp_s = jsonObject_projects.getString("start");
						jsonstamp_l = Long.valueOf(jsonstamp_s);
						dt = new Date(jsonstamp_l);
						formatStart = sdf.format(dt);
						if (jsonObject_projects.containsKey("end")) {
							jsonstamp_s = jsonObject_projects.getString("end");
							jsonstamp_l = Long.valueOf(jsonstamp_s);
							dt = new Date(jsonstamp_l);
							formatEnd = sdf.format(dt);
							System.out.println("[" + i + "][" + j
									+ "]description="
									+ jsonObject_projects.get("description")); // projectDescription

							System.out.println("[" + i + "][" + j + "]name="
									+ jsonObject_projects.get("name")); // projectName
							System.out
									.println("["
											+ i
											+ "]["
											+ j
											+ "]responsibility="
											+ jsonObject_projects
													.get("responsibility")); // projectResponsibility

							System.out.println("[" + i + "][" + j + "]start="
									+ formatStart); // projectStart

							System.out.println("[" + i + "][" + j + "]end="
									+ formatEnd); // projectEnd

						} else {
							System.out.println("[" + i + "][" + j
									+ "]description="
									+ jsonObject_projects.get("description")); // projectDescription

							System.out.println("[" + i + "][" + j + "]name="
									+ jsonObject_projects.get("name")); // projectName
							System.out
									.println("["
											+ i
											+ "]["
											+ j
											+ "]responsibility="
											+ jsonObject_projects
													.get("responsibility")); // projectResponsibility

							System.out.println("[" + i + "][" + j + "]start="
									+ formatStart); // projectStart
						}
					}

				}

				jsonstamp_s = jsonObject.getString("refreshAt");
				jsonstamp_l = Long.valueOf(jsonstamp_s);
				dt = new Date(jsonstamp_l);
				formatrefreshAt = sdf.format(dt);
				System.out.println("[" + i + "]refreshAt=" + formatrefreshAt); // refreshAt
				System.out.println("[" + i + "]resumeNo="
						+ jsonObject.get("resumeNo")); // resumeNo
				if (jsonObject.containsKey("schools")) {
					System.out.println("[" + i + "]schools:");
					tempString = jsonObject.get("schools").toString();
					JSONArray jsonArray_schools = JSONArray
							.fromObject(tempString);
					int size_schools = jsonArray_schools.size();
					for (int j = 0; j < size_schools; j++) {
						JSONObject jsonObject_schools = jsonArray_schools
								.getJSONObject(j);
						jsonstamp_s = jsonObject_schools.getString("start");
						jsonstamp_l = Long.valueOf(jsonstamp_s);
						dt = new Date(jsonstamp_l);
						formatStart = sdf.format(dt);
						if (jsonObject_schools.containsKey("end")) {
							jsonstamp_s = jsonObject_schools.getString("end");
							jsonstamp_l = Long.valueOf(jsonstamp_s);
							dt = new Date(jsonstamp_l);
							formatEnd = sdf.format(dt);
							System.out.println("[" + i + "][" + j + "]degree="
									+ jsonObject_schools.get("degree")); // schoolDegree

							System.out.println("[" + i + "][" + j + "]school="
									+ jsonObject_schools.get("school")); // schoolName

							System.out.println("[" + i + "][" + j + "]major="
									+ jsonObject_schools.get("major")); // schoolMajor

							System.out.println("[" + i + "][" + j + "]start="
									+ formatStart); // schoolStart
							System.out.println("[" + i + "][" + j + "]end="
									+ formatEnd); // schoolEnd
						} else {
							System.out.println("[" + i + "][" + j + "]degree="
									+ jsonObject_schools.get("degree")); // schoolDegree

							System.out.println("[" + i + "][" + j + "]school="
									+ jsonObject_schools.get("school")); // schoolName

							System.out.println("[" + i + "][" + j + "]major="
									+ jsonObject_schools.get("major")); // schoolMajor

							System.out.println("[" + i + "][" + j + "]start="
									+ formatStart); // schoolStart
						}
					}
				}

				System.out
						.println("[" + i + "]site =" + jsonObject.get("site")); // site
				jsonstamp_s = jsonObject.getString("updateAt");
				jsonstamp_l = Long.valueOf(jsonstamp_s);
				dt = new Date(jsonstamp_l);
				formatUpdateAt = sdf.format(dt);
				System.out.println("[" + i + "]updateAt ="
						+ jsonObject.get("updateAt")); // updateAt
				if (jsonObject.containsKey("works")) {
					System.out.println("[" + i + "]works:");
					tempString = jsonObject.get("works").toString();
					JSONArray jsonArray_works = JSONArray
							.fromObject(tempString);
					int size_works = jsonArray_works.size();

					for (int j = 0; j < size_works; j++) {
						JSONObject jsonObject_works = jsonArray_works
								.getJSONObject(j);
						jsonstamp_s = jsonObject_works.getString("start");
						jsonstamp_l = Long.valueOf(jsonstamp_s);
						dt = new Date(jsonstamp_l);
						formatStart = sdf.format(dt);
						if (jsonObject_works.containsKey("end")) {
							jsonstamp_s = jsonObject_works.getString("end");
							jsonstamp_l = Long.valueOf(jsonstamp_s);
							dt = new Date(jsonstamp_l);
							formatEnd = sdf.format(dt);
							System.out.println("[" + i + "][" + j + "]company="
									+ jsonObject_works.get("company")); // workCompany

							System.out.println("[" + i + "][" + j
									+ "]deccription="
									+ jsonObject_works.get("description")); // workDescription
							System.out.println("[" + i + "][" + j
									+ "]jobTitle="
									+ jsonObject_works.get("responsibility")); // workJobTitle
							System.out.println("[" + i + "][" + j + "]salary="
									+ jsonObject_works.get("salary")); // workSalary
							System.out.println("[" + i + "][" + j + "]start="
									+ formatStart); // workStart

							System.out.println("[" + i + "][" + j + "]end="
									+ formatEnd); // workEnd

						} else {
							System.out.println("[" + i + "][" + j + "]company="
									+ jsonObject_works.get("company")); // workCompany

							System.out.println("[" + i + "][" + j
									+ "]deccription="
									+ jsonObject_works.get("description")); // workDescription
							System.out.println("[" + i + "][" + j
									+ "]jobTitle="
									+ jsonObject_works.get("responsibility")); // workJobTitle
							System.out.println("[" + i + "][" + j + "]salary="
									+ jsonObject_works.get("salary")); // workSalary
							System.out.println("[" + i + "][" + j + "]start="
									+ formatStart); // workStart
						}
					}

				}
				i++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
