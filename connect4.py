from itertools import groupby, chain

NONE = '.'
RED = 'R'
YELLOW = 'Y'

def diagonalsPos (matrix, cols, rows):
	"""Get positive diagonals, going from bottom-left to top-right."""
	for di in ([(j, i - j) for j in range(cols)] for i in range(cols + rows -1)):
		yield [matrix[i][j] for i, j in di if i >= 0 and j >= 0 and i < cols and j < rows]

def diagonalsNeg (matrix, cols, rows):
	"""Get negative diagonals, going from top-left to bottom-right."""
	for di in ([(j, i - cols + j + 1) for j in range(cols)] for i in range(cols + rows - 1)):
		yield [matrix[i][j] for i, j in di if i >= 0 and j >= 0 and i < cols and j < rows]

import random
class Game:
	def __init__ (self, cols = 7, rows = 6, requiredToWin = 4):
		"""Create a new game."""
		self.cols = cols
		self.rows = rows
		self.win = requiredToWin
		self.board = [[NONE] * rows for _ in range(cols)]

	def insert (self, column, color):
		"""Insert the color in the given column."""
		c = self.board[column]
		if c[0] != NONE:
			print("Duursen")
			# raise Exception('Column is full')

		i = -1
		while c[i] != NONE:
			i -= 1
		c[i] = color

	def _dfs(self, x, y, color, visited, count):
		"""Depth-first search to check for consecutive colors."""
		if x < 0 or x >= self.cols or y < 0 or y >= self.rows or self.board[x][y] != color or visited[x][y]:
			return count
		visited[x][y] = True
		count += 1
		count = max(count, self._dfs(x + 1, y, color, visited, 0))
		count = max(count, self._dfs(x - 1, y, color, visited, 0))
		count = max(count, self._dfs(x, y + 1, color, visited, 0))
		count = max(count, self._dfs(x, y - 1, color, visited, 0))
		count = max(count, self._dfs(x + 1, y + 1, color, visited, 0))
		count = max(count, self._dfs(x - 1, y - 1, color, visited, 0))
		count = max(count, self._dfs(x + 1, y - 1, color, visited, 0))
		count = max(count, self._dfs(x - 1, y + 1, color, visited, 0))
		visited[x][y] = False
		return count

	def getWinner (self):
		"""Get the winner on the current board."""
		lines = (
			self.board, # columns
			zip(*self.board), # rows
			diagonalsPos(self.board, self.cols, self.rows), # positive diagonals
			diagonalsNeg(self.board, self.cols, self.rows) # negative diagonals
		)

		for line in chain(*lines):
			for color, group in groupby(line):
				if color != NONE and len(list(group)) >= self.win:
					return color

	""" AI тоглуулагчийн эсрэг хамгийн сайн хөдөлгөөнийг олох ."""
	def findBestMove(self):
		for col in range(self.cols):
			if self.isWinningMove(col, YELLOW):
				return col
		for col in range(self.cols):
			if self.isWinningMove(col, RED):
				return col
		return random.randint(0, self.cols - 1)

	"""Тодорхой баганад нүүдэл хийснээр ялалт байгуулах боломжтой эсэхийг шалгах function."""
	def isWinningMove(self, column, color):
		"""Check if making a move in a certain column results in a winning position."""
		temp_board = [row[:] for row in self.board]
		self.insert(column, color)
		winner = self.getWinner()
		self.board = temp_board
		return winner == color

	def getAIMove(self):
		"""Get the AI's move."""
		# Randomly select a column as the AI's move for demonstration
		empty_columns = [col for col in range(self.cols) if self.board[col][0] == NONE]
		return random.choice(empty_columns) if empty_columns else None

	def printBoard (self):
		"""Print the board."""
		print('  '.join(map(str, range(self.cols))))
		for y in range(self.rows):
			print('  '.join(str(self.board[x][y]) for x in range(self.cols)))
		print()


if __name__ == '__main__':
    g = Game()
    turn = RED
    while True:
        g.printBoard()
        if turn == RED:
            while True:
                try:
                    row = input('{}\'s turn: '.format('Red'))
                    column = int(row)
                    if 0 <= column < g.cols:
                        g.insert(column, turn)
                        winner = g.getWinner()
                        if winner:
                            print('{} won!'.format('Red'))
                            exit()
                        turn = YELLOW
                        break
                    else:
                        print("Column out of range! Please input a valid column number.")
                except ValueError:
                    print("Invalid input! Please input a valid column number.")
        else:
            col = g.findBestMove()
            g.insert(col, YELLOW)
            winner = g.getWinner()
            if winner:
        		# g.printBoard()
                print('{} won!'.format('Yellow'))
                exit()
            turn = RED
