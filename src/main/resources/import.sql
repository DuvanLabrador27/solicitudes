INSERT INTO employee (ds_document, ds_document_type, ds_name, ds_lastname, ds_phone_number, ds_address, fe_hire_date, fe_departure_date, ds_type_of_contract, ds_employee_status) VALUES ('123456789', 'CC', 'Leo', 'Messi', '1234567', 'Calle 123', '2024-02-01', NULL, 'INDEFINITE_TERM', 'Active');
INSERT INTO employee (ds_document, ds_document_type, ds_name, ds_lastname, ds_phone_number, ds_address, fe_hire_date, fe_departure_date, ds_type_of_contract, ds_employee_status) VALUES ('987654321', 'CE', 'Cristiano', 'Ronaldo', '7654321', 'Calle 321', '2023-01-01', NULL, 'FIXED_TERM', 'Active');
INSERT INTO employee (ds_document, ds_document_type, ds_name, ds_lastname, ds_phone_number, ds_address, fe_hire_date, fe_departure_date, ds_type_of_contract, ds_employee_status) VALUES ('123123123', 'PA', 'Neymar', 'Jr', '1231231', 'Calle 123', '2023-01-01', NULL, 'OPS', 'Active');
INSERT INTO employee (ds_document, ds_document_type, ds_name, ds_lastname, ds_phone_number, ds_address, fe_hire_date, fe_departure_date, ds_type_of_contract, ds_employee_status) VALUES ('321321321', 'CE', 'Kiliam', 'Mbappe', '3213213', 'Calle 321', '2023-01-01', NULL, 'APPRENTICESHIP', 'Inactive');
INSERT INTO employee (ds_document, ds_document_type, ds_name, ds_lastname, ds_phone_number, ds_address, fe_hire_date, fe_departure_date, ds_type_of_contract, ds_employee_status) VALUES ('456456456', 'CC', 'Dayro', 'Moreno', '4564564', 'Calle 456', '2023-01-01', NULL, 'INDEFINITE_TERM', 'Active');

INSERT INTO users (ds_username, ds_password, email,ds_user_status, nm_id_employee) VALUES ('lmessi', '123','messi@gmail.com','Active', 1);
INSERT INTO users (ds_username, ds_password, email,ds_user_status, nm_id_employee) VALUES ('CR7', '123','cr7@gmail.com','Active', 2);

INSERT INTO request_vacation (name_request,description,fe_start_date, fe_end_date,fe_reinstatement_date,nm_number_of_days_requested,nm_id_user) VALUES ('NAME REQUEST 1','THIS IS THE FIRST REQUEST','2023-01-01', '2023-01-05', '2023-01-06', 5, 1);
