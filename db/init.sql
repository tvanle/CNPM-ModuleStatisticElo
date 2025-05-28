-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS chess;
USE chess;

-- Tạo bảng Tournament
CREATE TABLE Tournament (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    year INT,
    edition INT,
    location VARCHAR(100),
    description VARCHAR(150)
);

-- Tạo bảng Manager
CREATE TABLE Manager (
    id VARCHAR(50) PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50),
    tournamentId VARCHAR(50),
    FOREIGN KEY (tournamentId) REFERENCES Tournament(id)
);

-- Tạo bảng Round
CREATE TABLE Round (
    id VARCHAR(50) PRIMARY KEY,
    roundNum INT,
    startDate DATE,
    endDate DATE,
    tournamentId VARCHAR(50),
    FOREIGN KEY (tournamentId) REFERENCES Tournament(id)
);

-- Tạo bảng ChessPlayer
CREATE TABLE ChessPlayer (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    nationality VARCHAR(50),
    birthYear INT,
    icard VARCHAR(50),
    initialElo FLOAT
);

-- Tạo bảng Match (dùng backticks để thoát từ khóa Match)
CREATE TABLE `Match` (
    id VARCHAR(50) PRIMARY KEY,
    date DATE,
    roundId VARCHAR(50),
    FOREIGN KEY (roundId) REFERENCES Round(id)
);

-- Tạo bảng MatchPlayer
CREATE TABLE MatchPlayer (
    id VARCHAR(50) PRIMARY KEY,
    eloChange FLOAT,
    result VARCHAR(1),
    matchId VARCHAR(50),
    chessPlayerId VARCHAR(50),
    FOREIGN KEY (matchId) REFERENCES `Match`(id),
    FOREIGN KEY (chessPlayerId) REFERENCES ChessPlayer(id)
);