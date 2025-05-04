-- Schema for DietitianTrackingApp

-- Insert Users
INSERT INTO users (id, name, surname, email, password, full_name, role, created_at, updated_at) 
VALUES (1, 'Admin', 'User', 'admin@dietitian.com', 'password123', 'Admin User', 'DIETITIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (id, name, surname, email, password, full_name, role, created_at, updated_at) 
VALUES (2, 'John', 'Doe', 'john.doe@dietitian.com', 'password123', 'John Doe', 'DIETITIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (id, name, surname, email, password, full_name, role, created_at, updated_at) 
VALUES (3, 'Jane', 'Smith', 'jane.smith@dietitian.com', 'password123', 'Jane Smith', 'DIETITIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (id, name, surname, email, password, full_name, role, created_at, updated_at) 
VALUES (4, 'Alice', 'Johnson', 'alice.johnson@example.com', 'password123', 'Alice Johnson', 'PATIENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (id, name, surname, email, password, full_name, role, created_at, updated_at) 
VALUES (5, 'Bob', 'Williams', 'bob.williams@example.com', 'password123', 'Bob Williams', 'PATIENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (id, name, surname, email, password, full_name, role, created_at, updated_at) 
VALUES (6, 'Carol', 'Brown', 'carol.brown@example.com', 'password123', 'Carol Brown', 'PATIENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Dietitians
INSERT INTO dietitians (id, user_id, title, specialization, license_number, biography) 
VALUES (1, 2, 'Registered Dietitian', 'Weight Management', 'RD12345', 'John Doe is a registered dietitian specializing in weight management with over 10 years of experience.');

INSERT INTO dietitians (id, user_id, title, specialization, license_number, biography) 
VALUES (2, 3, 'Clinical Dietitian', 'Sports Nutrition', 'RD67890', 'Jane Smith is a clinical dietitian specializing in sports nutrition, helping athletes achieve optimal performance through proper nutrition.');

-- Insert Patients
INSERT INTO patients (id, user_id, dietitian_id, birth_date, gender, height) 
VALUES (1, 4, 1, '1990-05-15', 'FEMALE', 165.0);

INSERT INTO patients (id, user_id, dietitian_id, birth_date, gender, height) 
VALUES (2, 5, 1, '1985-08-22', 'MALE', 180.0);

INSERT INTO patients (id, user_id, dietitian_id, birth_date, gender, height) 
VALUES (3, 6, 2, '1995-03-10', 'FEMALE', 170.0);

-- Insert Weight Records
INSERT INTO weight_records (id, patient_id, weight, muscle_weight, fat_weight, bone_weight, muscle_ratio, fat_ratio, body_water_ratio, bmi_score, record_date, notes) 
VALUES (1, 1, 70.5, 28.2, 21.1, 10.5, 40.0, 30.0, 55.0, 24.5, CURRENT_TIMESTAMP, 'Initial weight measurement');

INSERT INTO weight_records (id, patient_id, weight, muscle_weight, fat_weight, bone_weight, muscle_ratio, fat_ratio, body_water_ratio, bmi_score, record_date, notes) 
VALUES (2, 1, 69.8, 28.5, 20.5, 10.5, 40.8, 29.4, 55.5, 24.2, CURRENT_TIMESTAMP - INTERVAL '7 days', 'Weight after first week of diet plan');

INSERT INTO weight_records (id, patient_id, weight, muscle_weight, fat_weight, bone_weight, muscle_ratio, fat_ratio, body_water_ratio, bmi_score, record_date, notes) 
VALUES (3, 2, 85.2, 42.6, 25.5, 12.8, 50.0, 30.0, 60.0, 26.3, CURRENT_TIMESTAMP, 'Initial weight measurement');

INSERT INTO weight_records (id, patient_id, weight, muscle_weight, fat_weight, bone_weight, muscle_ratio, fat_ratio, body_water_ratio, bmi_score, record_date, notes) 
VALUES (4, 3, 65.0, 26.0, 19.5, 9.7, 40.0, 30.0, 55.0, 22.5, CURRENT_TIMESTAMP, 'Initial weight measurement');

-- Insert Nutrition Plans
INSERT INTO nutrition_plans (id, patient_id, dietitian_id, title, start_date, end_date, description, daily_calorie_target, protein_target, carbs_target, fat_target, recommendations, created_at, updated_at) 
VALUES (1, 1, 1, 'Weight Loss Plan', CURRENT_DATE, CURRENT_DATE + INTERVAL '3 months', 'A balanced diet plan for gradual weight loss', 1800, 90.0, 200.0, 60.0, 'Focus on whole foods, limit processed foods, and stay hydrated.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO nutrition_plans (id, patient_id, dietitian_id, title, start_date, end_date, description, daily_calorie_target, protein_target, carbs_target, fat_target, recommendations, created_at, updated_at) 
VALUES (2, 2, 1, 'Muscle Gain Plan', CURRENT_DATE, CURRENT_DATE + INTERVAL '4 months', 'High protein diet for muscle building', 2500, 150.0, 250.0, 70.0, 'Consume protein with each meal, focus on post-workout nutrition.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO nutrition_plans (id, patient_id, dietitian_id, title, start_date, end_date, description, daily_calorie_target, protein_target, carbs_target, fat_target, recommendations, created_at, updated_at) 
VALUES (3, 3, 2, 'Balanced Nutrition Plan', CURRENT_DATE, CURRENT_DATE + INTERVAL '2 months', 'Balanced diet for overall health', 2000, 100.0, 220.0, 65.0, 'Eat a variety of colorful fruits and vegetables, limit added sugars.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Meal Plans
INSERT INTO meal_plans (id, nutrition_plan_id, meal_type, food_items, calories, protein, carbs, fat, notes) 
VALUES (1, 1, 'BREAKFAST', 'Oatmeal with berries and nuts', 350, 15.0, 45.0, 12.0, 'Use steel-cut oats for better nutrition');

INSERT INTO meal_plans (id, nutrition_plan_id, meal_type, food_items, calories, protein, carbs, fat, notes) 
VALUES (2, 1, 'LUNCH', 'Grilled chicken salad with olive oil dressing', 450, 35.0, 20.0, 25.0, 'Add a variety of colorful vegetables');

INSERT INTO meal_plans (id, nutrition_plan_id, meal_type, food_items, calories, protein, carbs, fat, notes) 
VALUES (3, 1, 'DINNER', 'Baked salmon with quinoa and steamed vegetables', 550, 40.0, 45.0, 20.0, 'Wild-caught salmon preferred');

INSERT INTO meal_plans (id, nutrition_plan_id, meal_type, food_items, calories, protein, carbs, fat, notes) 
VALUES (4, 1, 'SNACK', 'Greek yogurt with honey', 200, 15.0, 20.0, 5.0, 'Choose low-fat, unsweetened yogurt');

INSERT INTO meal_plans (id, nutrition_plan_id, meal_type, food_items, calories, protein, carbs, fat, notes) 
VALUES (5, 2, 'BREAKFAST', 'Protein smoothie with banana and peanut butter', 500, 30.0, 50.0, 15.0, 'Use whey protein powder');

INSERT INTO meal_plans (id, nutrition_plan_id, meal_type, food_items, calories, protein, carbs, fat, notes) 
VALUES (6, 2, 'LUNCH', 'Turkey and avocado sandwich on whole grain bread', 600, 40.0, 50.0, 25.0, 'Use lean turkey breast');

INSERT INTO meal_plans (id, nutrition_plan_id, meal_type, food_items, calories, protein, carbs, fat, notes) 
VALUES (7, 2, 'DINNER', 'Beef stir-fry with brown rice and vegetables', 700, 45.0, 70.0, 20.0, 'Use lean beef cuts');

INSERT INTO meal_plans (id, nutrition_plan_id, meal_type, food_items, calories, protein, carbs, fat, notes) 
VALUES (8, 3, 'BREAKFAST', 'Vegetable omelet with whole grain toast', 400, 25.0, 30.0, 20.0, 'Use a variety of colorful vegetables');

INSERT INTO meal_plans (id, nutrition_plan_id, meal_type, food_items, calories, protein, carbs, fat, notes) 
VALUES (9, 3, 'LUNCH', 'Lentil soup with mixed green salad', 450, 20.0, 60.0, 10.0, 'Add a variety of herbs for flavor');

INSERT INTO meal_plans (id, nutrition_plan_id, meal_type, food_items, calories, protein, carbs, fat, notes) 
VALUES (10, 3, 'DINNER', 'Grilled tofu with sweet potato and broccoli', 500, 25.0, 65.0, 15.0, 'Marinate tofu for better flavor');

-- Insert Meal Records
INSERT INTO meal_records (id, patient_id, meal_type, meal_date_time, food_items, calories, protein, carbs, fat, notes, image_url) 
VALUES (1, 1, 'BREAKFAST', CURRENT_TIMESTAMP - INTERVAL '4 hours', 'Oatmeal with berries', 320, 12.0, 40.0, 10.0, 'Felt full until lunch', NULL);

INSERT INTO meal_records (id, patient_id, meal_type, meal_date_time, food_items, calories, protein, carbs, fat, notes, image_url) 
VALUES (2, 1, 'LUNCH', CURRENT_TIMESTAMP - INTERVAL '1 hour', 'Chicken salad', 420, 30.0, 15.0, 22.0, 'Added extra olive oil', NULL);

INSERT INTO meal_records (id, patient_id, meal_type, meal_date_time, food_items, calories, protein, carbs, fat, notes, image_url) 
VALUES (3, 2, 'BREAKFAST', CURRENT_TIMESTAMP - INTERVAL '5 hours', 'Protein shake with banana', 450, 35.0, 45.0, 10.0, 'Used whey protein', NULL);

INSERT INTO meal_records (id, patient_id, meal_type, meal_date_time, food_items, calories, protein, carbs, fat, notes, image_url) 
VALUES (4, 3, 'DINNER', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Grilled tofu with vegetables', 480, 25.0, 60.0, 15.0, 'Added soy sauce for flavor', NULL);

-- Insert Appointments
INSERT INTO appointments (id, patient_id, dietitian_id, appointment_date_time, status, notes) 
VALUES (1, 1, 1, CURRENT_TIMESTAMP + INTERVAL '3 days', 'SCHEDULED', 'Initial consultation');

INSERT INTO appointments (id, patient_id, dietitian_id, appointment_date_time, status, notes) 
VALUES (2, 2, 1, CURRENT_TIMESTAMP + INTERVAL '5 days', 'SCHEDULED', 'Follow-up appointment');

INSERT INTO appointments (id, patient_id, dietitian_id, appointment_date_time, status, notes) 
VALUES (3, 3, 2, CURRENT_TIMESTAMP - INTERVAL '7 days', 'COMPLETED', 'Initial consultation');

-- Insert Consultations
INSERT INTO consultations (id, appointment_id, findings, recommendations, next_steps, created_at, updated_at) 
VALUES (1, 3, 'Patient is generally healthy but needs to improve diet quality', 'Increase vegetable intake, reduce processed foods', 'Follow up in 2 weeks to assess progress', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
