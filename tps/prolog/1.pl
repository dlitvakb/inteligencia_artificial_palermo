% Hechos Simples

man(adam).
man(peter).
man(joe).
man(john).
man(terry).
man(arnold).

woman(eve).
woman(sol).
woman(luz).
woman(ana).

% Hechos relacionados

father(adam, joe).
father(adam, john).
father(peter, adam).
father(peter, sol).
father(peter, luz).

mother(eve, joe).
mother(eve, john).
mother(ana, eve).
mother(ana, luz).
mother(eve, sol).

% Reglas

is_father(F) :- father(F, _).
is_mother(M) :- mother(M, _).

parent(P, Y) :- father(P, Y); mother(P, Y).

is_parent(P) :- parent(P, _).

sibilings(X, Y) :- parent(Z, X), parent(Z, Y), X \= Y.

is_son(S) :- man(S), parent(_, S).
is_daughter(D) :- woman(D), parent(_, D).

is_grand_parent(GP) :- parent(GP, X), parent(X, _).

% --

sum(0, 0).
sum(N, S) :- M is N - 1, sum(M, S1), S is S1 + N.
