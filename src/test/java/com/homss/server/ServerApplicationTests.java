package com.homss.server;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@Sql({"classpath:initTable.sql"})
@SpringBootTest
public class ServerApplicationTests {
}
