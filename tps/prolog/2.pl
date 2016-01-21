factorial(0, 1).
factorial(X, R) :-
  X1 is X - 1,
  factorial(X1, R1),
  R is R1 * X.

fibonacci(0, 0).
fibonacci(1, 1).
fibonacci(N, R) :-
  N > 1,
  N2 is N - 2, fibonacci(N2, Y2),
  N1 is N - 1, fibonacci(N1, Y1),
  R is Y1 + Y2.

size([], 0).
size([_|T], R) :-
  size(T, R1),
  R is R1 + 1.

sumList([], 0).
sumList([H|T], R) :-
  sumList(T, R1),
  R is R1 + H.

primero([H|_], H).

prodList([], 1).
prodList([H|T], R) :-
  prodList(T, R1),
  R is R1 * H.

rotar([], []).
rotar([H|T], R) :-
  rotar(T, R1),
  append(R1, [H], R).

igualLista([], []).
igualLista([H1|T1], [H2|T2]) :-
  H1 == H2,
  igualLista(T1, T2).

is_palindromo(A, B) :-
  rotar(A, R),
  igualLista(B, R).
