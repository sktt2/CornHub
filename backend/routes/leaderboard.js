const express = require('express')
const router = express.Router();
const db = require('../database/db')

router.get("/", (req, res) => {
    db.query("SELECT * FROM leaderboard ORDER BY score desc limit 7", function (err, result, fields) {
        if (err) throw err;
        return res.send(result);
    });
});

router.get("/:tagId", (req, res) => {
    id = req.params.tagId
    db.query(`SELECT id, score, (SELECT COUNT(*) + 1 FROM leaderboard s2 WHERE s2.score > s1.score) AS position FROM leaderboard s1 WHERE id = '${id}';`, function (err, result, fields) {
        if (err) throw err;
        return res.send(result);
    });
});

router.post("/", (req, res) => {
    var { id, score } = req.body
    id = id.toUpperCase()
    const query = `INSERT INTO leaderboard (id, score) VALUES ('${id}', '${score}') ON DUPLICATE KEY UPDATE score = ${score};`
    db.query(query, function (err, result, fields) {
        if (err) throw err;
    });
    db.query(`SELECT * FROM leaderboard WHERE id='${id}'`, function (err, result, fields) {
        if (err) throw err;
        return res.send(result);
    });
});

module.exports = router;