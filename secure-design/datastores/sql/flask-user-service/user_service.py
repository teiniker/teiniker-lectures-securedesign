from http import HTTPStatus
from flask import Flask, request, jsonify
import sqlite3

app = Flask(__name__)

DATABASE = 'user.db'

@app.route('/users/<username>', methods=['GET'])
def get_user(username):
    conn = sqlite3.connect(DATABASE)
    c = conn.cursor()
    c.execute(f"SELECT id, username, email, password FROM users WHERE username LIKE '{username}'")
    row = c.fetchone()
    conn.close()

    if row:
        user = {
            'id': row[0],
            'username': row[1],
            'email': row[2]
        }
        return jsonify(user), HTTPStatus.OK
    else:
        return jsonify({'error': 'User not found'}), HTTPStatus.NOT_FOUND


if __name__ == '__main__':
    app.run(port=8080, debug=True)

