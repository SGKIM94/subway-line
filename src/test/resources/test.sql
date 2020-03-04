INSERT INTO USER (name, email, password) VALUES ('김상구', 'sgkim94@github.com', '123123');
INSERT INTO STATION (id, name) VALUES ('1', '강남역'), ('2', '역삼역');
INSERT INTO LINE (id, name, start_time, end_time, interval_time) VALUES ('1', '2호선', '00:00', '23:30', '10');
INSERT INTO EDGE (id, line_id, source_station_id, target_station_id, distance) VALUES ('1', '1', '1', '2', '10');


