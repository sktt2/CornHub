const mysql = require("mysql2");

const database = () => {

    const db = mysql.createConnection({
        host: process.env.HOST,
        port: process.env.DBPORT,
        user: process.env.USERNAME,
        password: process.env.PASSWORD,
    });

    const leaderboard = `CREATE TABLE IF NOT EXISTS leaderboard (id VARCHAR(50) NOT NULL, score INT NOT NULL, PRIMARY KEY(id) );`;
    const query = "INSERT INTO leaderboard (id, score) SELECT * FROM ( SELECT 'DEXTER', '1000' UNION ALL SELECT 'DONGXIAN', '999' UNION ALL SELECT 'FRANCIS', '998' UNION ALL SELECT 'BAOJIE', '997' UNION ALL SELECT 'TIMOTHY', '500' ) AS FOO  WHERE NOT EXISTS (SELECT * FROM leaderboard);"

    db.connect(function (err) {
        if (err) throw err;
        db.query(`CREATE DATABASE IF NOT EXISTS ${process.env.DATABASE}`, function (err, result) {
            if (err) throw err;
            console.log("Database created");
        });
        db.query(`use ${process.env.DATABASE}`, function (err, result, fields) {
            if (err) throw err;
        });
        db.query(leaderboard, function (err, result, fields) {
            if (err) throw err;
        });
        db.query(query, function (err, result, fields) {
            if (err) throw err;
        });

    });
    return db
}
module.exports = database()