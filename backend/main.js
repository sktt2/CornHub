require("dotenv").config();

const express = require("express");
const bodyParser = require('body-parser');
const leaderboardRoutes = require('./routes/leaderboard')
const app = express();
app.use(bodyParser.json())

app.use("/leaderboard", leaderboardRoutes)

app.listen(process.env.PORT, () =>
	console.log(`Example app listening on port ${process.env.PORT}!`)
);
