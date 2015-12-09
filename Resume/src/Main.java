import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class <code>Main</code> is a class which extracts the data in resume and
 * imports them to MySql database resume. For the Project <code>Resume</code>.
 * 
 * @author Liu Yuchen, integrated by Su Chang
 * @version 05/15/2015 GMT 16:15
 * @see json-lib, java.sql.*
 * @since JDK 1.8
 * @Encoding utf-8
 **/
public class Main {
	public static void main(String[] args) {
		BufferedReader reader = null;
		// 处理IOException, SQLException, ClassNotFoundException
		try {
			// 加载MySQL驱动类
			Class.forName("com.mysql.jdbc.Driver");

			// 以下信息需要根据本机信息修改
			String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8";
			String username = "root";
			String password = "suchang123";

			// 连接数据库
			Connection connect = DriverManager.getConnection(url, username,
					password);

			// json中的日期类型需要转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			String formatBirthday;
			String formatStart;
			String formatEnd;
			String formatrefreshAt;

			// 读取数据
			FileInputStream fileInputStream = new FileInputStream(
					"/Users/mac/Desktop/splitFile/0.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "utf-8");
			reader = new BufferedReader(inputStreamReader);

			// 分析数据
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				tempString = tempString.replace("#RESUMEID#", "[");
				tempString += "]";

				JSONArray jsonArray = JSONArray.fromObject(tempString);
				tempString = "";
				JSONObject jsonObject = jsonArray.getJSONObject(0);

				// 分析birthday
				String jsonstamp_s = jsonObject.getString("birthday");
				long jsonstamp_l = Long.valueOf(jsonstamp_s);
				Date dt = new Date(jsonstamp_l);
				// birthday转换成时间戳格式, 但是由于时间都是00:00:00所以直接删去
				formatBirthday = sdf.format(dt).split(" ")[0];

				// 分析gender
				int gender = Integer.parseInt(jsonObject.get("gender")
						.toString());

				// 分析married
				int married = Integer.parseInt(jsonObject.get("married")
						.toString());

				// 分析resumeId
				jsonstamp_s = jsonObject.getString("refreshAt");
				jsonstamp_l = Long.valueOf(jsonstamp_s);
				dt = new Date(jsonstamp_l);
				formatrefreshAt = sdf.format(dt);
				String resumeId = jsonObject.get("resumeId").toString();

				// 正则提取数字、字母、汉字
				String regEx = "[^(a-zA-Z0-9\\u4e00-\\u9fa5)]";

				// 由于工作和学校信息会有很多组, 所以利用数组存储每次循环提取出的信息
				String jobTitle[] = null;
				String company[] = null;
				String start[] = null;
				String end[] = null;
				int size_job = 0;
				String school[] = null;
				String degree[] = null;
				String s_start[] = null;
				String s_end[] = null;
				int size_schools = 0;

				// 判断该简历中是否含有工作信息
				if (jsonObject.containsKey("jobs")) {

					tempString = jsonObject.get("jobs").toString();
					JSONArray jsonArray_job = JSONArray.fromObject(tempString);
					size_job = jsonArray_job.size();

					// 赋值数组大小
					jobTitle = new String[size_job];
					company = new String[size_job];
					start = new String[size_job];
					end = new String[size_job];

					for (int j = 0; j < size_job; j++) {
						JSONObject jsonObject_job = jsonArray_job
								.getJSONObject(j);
						jsonstamp_s = jsonObject_job.getString("start");
						jsonstamp_l = Long.valueOf(jsonstamp_s);
						dt = new Date(jsonstamp_l);
						formatStart = sdf.format(dt);

						// 分析是否含有endDate
						if (jsonObject_job.containsKey("end")) {
							jsonstamp_s = jsonObject_job.getString("end");
							jsonstamp_l = Long.valueOf(jsonstamp_s);
							dt = new Date(jsonstamp_l);
							formatEnd = sdf.format(dt);
							end[j] = formatEnd.split(" ")[0];
						} else {
							end[j] = null;
						}
						jobTitle[j] = jsonObject_job.get("jobTitle").toString()
								.replaceAll(regEx, "");
						company[j] = jsonObject_job.get("company").toString()
								.replaceAll(regEx, "");
						start[j] = formatStart.split(" ")[0];
					}

				} else {
					// 没有工作信息则数组大小为0
					jobTitle = new String[0];
					company = new String[0];
					start = new String[0];
					end = new String[0];
				}

				// 判断该简历中是否含有学校信息
				if (jsonObject.containsKey("schools")) {
					
					tempString = jsonObject.get("schools").toString();
					JSONArray jsonArray_schools = JSONArray
							.fromObject(tempString);
					size_schools = jsonArray_schools.size();

					// 赋值数组大小
					school = new String[size_schools];
					degree = new String[size_schools];
					s_start = new String[size_schools];
					s_end = new String[size_schools];

					for (int j = 0; j < size_schools; j++) {
						JSONObject jsonObject_schools = jsonArray_schools
								.getJSONObject(j);
						jsonstamp_s = jsonObject_schools.getString("start");
						jsonstamp_l = Long.valueOf(jsonstamp_s);
						dt = new Date(jsonstamp_l);
						formatStart = sdf.format(dt);

						// 分析是否含有endDate
						if (jsonObject_schools.containsKey("end")) {
							jsonstamp_s = jsonObject_schools.getString("end");
							jsonstamp_l = Long.valueOf(jsonstamp_s);
							dt = new Date(jsonstamp_l);
							formatEnd = sdf.format(dt);
							s_end[j] = formatEnd.split(" ")[0];
						} else {
							s_end[j] = null;
						}
						school[j] = jsonObject_schools.get("school").toString()
								.replaceAll(regEx, "");
						degree[j] = jsonObject_schools.get("degree").toString()
								.replaceAll(regEx, "");
						s_start[j] = formatStart.toString().split(" ")[0];
					}
				} else {
					// 没有学校信息则数组大小为0
					school = new String[0];
					degree = new String[0];
					s_start = new String[0];
					s_end = new String[0];
				}

				// 赋值sql语句
				String sql = "INSERT INTO resume.INFORMATION (id,sex,marriage,birthdate";
				String values = resumeId + "," + gender + "," + married + ","
						+ "'" + formatBirthday + "'";
				for (int k = 0; k < size_schools; k++) {
					sql += "," + "school_" + (k + 1);
					sql += "," + "academic_" + (k + 1);
					sql += "," + "edu_fromDate_" + (k + 1);
					sql += "," + "edu_toDate_" + (k + 1);
					values += "," + "'" + school[k] + "'";
					values += "," + "'" + degree[k] + "'";
					values += "," + "'" + s_start[k] + "'";
					if (s_end[k] == null) {
						values += "," + "NULL";
					} else {
						values += "," + "'" + s_end[k] + "'";
					}
				}
				for (int k = 0; k < size_job; k++) {
					sql += "," + "jobExp" + (k + 1) + "_companyName";
					sql += "," + "jobExp" + (k + 1) + "_position";
					sql += "," + "jobExp" + (k + 1) + "_fromDate";
					sql += "," + "jobExp" + (k + 1) + "_toDate";
					values += "," + "'" + company[k] + "'";
					values += "," + "'" + jobTitle[k] + "'";
					values += "," + "'" + start[k] + "'";
					if (end[k] == null) {
						values += "," + "NULL";
					} else {
						values += "," + "'" + end[k] + "'";
					}
				}
				sql += ") VALUES (" + values + ")";
				System.out.println(sql);

				// 执行sql语句
				try {
					PreparedStatement psql = connect.prepareStatement(sql);
					psql.executeUpdate();
				} catch (SQLException se) {
					// 如果发生异常则跳过该条简历继续循环
					continue;
				}
			}
			reader.close();
		} catch (SQLException e) {
			// 处理异常
			System.out.println("Could not connect to database!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Could not fine the Driver Class!");
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
