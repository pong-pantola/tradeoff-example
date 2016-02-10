/**
 * Copyright 2015 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package net.tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.TradeoffAnalytics;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Dilemma;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Option;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Problem;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.Column;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.Column.Goal;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.NumericColumn;

public class TradeoffAnalyticsExample {

  public static void main(String[] args) {
    TradeoffAnalytics service = new TradeoffAnalytics();

    //1. CREATE A TRADEOFF-ANALYTICS SERVICE THEN BIND IT TO ANY APPLICATION 
    //2. YOU NEED TO BIND THIS TO AN APPLICATION SO THAT THE APPLICATION WILL GET THE CREDENTIALS OF TRADEOFF-ANALYTICS SERVICE
    //3. CHECK THE ENVIRONMENT VARIABLE OF THE APPLICATION AND LOOK FOR THE CREDENTIALS OF TRADEOFF ANALYTICS.
    //4. CHANGE THE USERNAME AND PASSWORD BELOW WITH THE CREDENTIALS YOU FOUND IN THE ENVIRONMENT VARIABLES
    String username = "d7932fc3-5fb4-452a-926a-437494a357bb";
    String password = "sHtl2NECnYgc";
    service.setUsernameAndPassword(username, password);

    Problem problem = new Problem("phone");

    String price = "price";
    String ram = "ram";
    String screen = "screen";

    // Define the objectives
    List<Column> columns = new ArrayList<Column>();
    problem.setColumns(columns);

    columns.add(new NumericColumn().withRange(0, 100).withKey(price).withGoal(Goal.MIN)
        .withObjective(true));
    columns.add(new NumericColumn().withKey(screen).withGoal(Goal.MAX).withObjective(true));
    columns.add(new NumericColumn().withKey(ram).withGoal(Goal.MAX));

    // Define the options to choose
    List<Option> options = new ArrayList<Option>();
    problem.setOptions(options);

    HashMap<String, Object> galaxySpecs = new HashMap<String, Object>();
    galaxySpecs.put(price, 50);
    galaxySpecs.put(ram, 45);
    galaxySpecs.put(screen, 5);
    options.add(new Option("1", "Galaxy S4").withValues(galaxySpecs));

    HashMap<String, Object> iphoneSpecs = new HashMap<String, Object>();
    iphoneSpecs.put(price, 99);
    iphoneSpecs.put(ram, 40);
    iphoneSpecs.put(screen, 4);
    options.add(new Option("2", "iPhone 5").withValues(iphoneSpecs));

    HashMap<String, Object> testSpecs = new HashMap<String, Object>();
    //I exaggerated the price (lowest), ram (largest), and screen (largest) so that this is the clear winner
    testSpecs.put(price, 1);
    testSpecs.put(ram, 30000);
    testSpecs.put(screen, 5000);
    options.add(new Option("3", "MyPhone").withValues(testSpecs));

    HashMap<String, Object> optimusSpecs = new HashMap<String, Object>();
    optimusSpecs.put(price, 10);
    optimusSpecs.put(ram, 300);
    optimusSpecs.put(screen, 5);
    options.add(new Option("4", "LG Optimus G").withValues(optimusSpecs));





    // Call the service and get the resolution
    Dilemma dilemma = service.dilemmas(problem);

    //Look at the screen output and notice that the winner is option 3
    System.out.println(dilemma);
  }
}
