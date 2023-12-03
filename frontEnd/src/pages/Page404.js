import React from "react";
import { Link } from "react-router-dom";

const Page404 = () => {
  return (
    <div style={styles.container}>
      <h1 style={styles.heading}>404 - Not Found</h1>
      <p style={styles.message}>
        Oops! The page you are looking for might be in another castle.
      </p>
      <p style={styles.suggestion}>
        You can go back to the{" "}
        <Link to="/app" style={styles.link}>
          home page
        </Link>{" "}
        or try searching for what you need.
      </p>
    </div>
  );
};

const styles = {
  container: {
    textAlign: "center",
    marginTop: "50px",
  },
  heading: {
    fontSize: "48px",
    color: "#d9534f", // Bootstrap danger color
    marginBottom: "10px",
  },
  message: {
    fontSize: "18px",
    color: "#777",
    marginBottom: "20px",
  },
  suggestion: {
    fontSize: "16px",
    color: "#777",
  },
  link: {
    color: "#5bc0de", // Bootstrap info color
    textDecoration: "underline",
  },
};

export default Page404;
