from __future__ import print_function

import random
import math

BLOCKED = u'\u2588'
EMPTY = " "
START = "A"
FINISH = "B"

def create_map(width, length):
    tiles = []
    for y in xrange(length):
        for x in xrange(width):
            tiles.append({"x": x, "y": y, "which": EMPTY})

    return tiles

def print_map(tiles):
    current_y = -1
    lines = []
    line = []
    for tile in tiles:
        if current_y != tile["y"]:
            if len(line) > 0:
                lines.append(line)
            line = []
            current_y = tile["y"]
        line.append(tile["which"])

    for line in lines:
        print("".join(line))

def get_tile(tiles, x, y):
    for tile in tiles:
        if tile["x"] == x and tile["y"] == y:
            return tile
    raise Exception("Tile Not Found")

def get_tile_by_type(tiles, which):
    for tile in tiles:
        if tile["which"] == which:
            return tile
    raise Exception("Tile Not Found")

def set_point(tiles, which, x, y):
    tile = get_tile(tiles, x, y)

    if which in [START, FINISH, BLOCKED, EMPTY]:
        tile["which"] = which
    else:
        raise Exception("Invalid Tile Type")

def get_adjacent(tiles, tile):
    adjacent = []
    for dy in [-1, 0, 1]:
        for dx in [-1, 0, 1]:
            if dy == 0 and dx == 0:
                continue
            try:
                adjacent_tile = get_tile(tiles, tile["x"] + dx, tile["y"] + dy)
                if adjacent_tile["which"] == BLOCKED:
                    continue
                adjacent.append(adjacent_tile)
            except:
                continue
    return adjacent

def calculate_g(node, tile, weight):
    diagonal_weight = int(math.sqrt(2 * weight**2))
    node_tile = node["tile"]
    g = node.get("g", 0)
    if node_tile["x"] != tile["x"] and node_tile["y"] != tile["y"]:
        return g + diagonal_weight
    return g + weight

def calculate_h(tile, to_tile, weight):
    dx = abs(tile["x"] - to_tile["x"])
    dy = abs(tile["y"] - to_tile["y"])
    return (dx + dy) * weight

def proccess_adjacent(tiles, nodes, node, from_tile, to_tile, weight):
    for tile in get_adjacent(tiles, node["tile"]):
        adjacent_node = {
                "tile": tile,
                "g": calculate_g(node, tile, weight),
                "h": calculate_h(tile, to_tile, weight),
                "points": node
               }
        adjacent_node["F"] = adjacent_node["g"] + adjacent_node["h"]

        found_node = None
        for _node in nodes:
            if tile == _node["tile"]:
                found_node = _node

        if found_node is not None:
            if found_node["F"] > adjacent_node["F"]:
                nodes[nodes.index(found_node)] = adjacent_node
        else:
            nodes.append(adjacent_node)

def get_next_node_to_process(processed_nodes, closed_nodes):
    min_f_node = None
    for node in processed_nodes:
        if node not in closed_nodes:
            if min_f_node is None:
                min_f_node = node
            elif min_f_node["F"] > node["F"]:
                min_f_node = node

    return min_f_node

def node_for(nodes, tile):
    for node in nodes:
        if node["tile"] == tile:
            return node

def path_from_nodes(from_tile, to_tile, closed_nodes):
    path = []

    next_node = node_for(closed_nodes, to_tile)
    while next_node is not None:
        path.append(next_node["tile"])
        next_node = next_node.get("points")

    for tile in path:
        if tile == from_tile or tile == to_tile:
            continue
        tile["which"] = "o"


def astar(tiles, weight=10):
    from_tile = get_tile_by_type(tiles, START)
    to_tile = get_tile_by_type(tiles, FINISH)

    closed_nodes = []
    processed = []

    node_to_process = {"tile": from_tile}
    while node_to_process["tile"] != to_tile:
        closed_nodes.append(node_to_process)
        proccess_adjacent(tiles, processed, node_to_process, from_tile, to_tile, weight)

        node_to_process = get_next_node_to_process(processed, closed_nodes)
    closed_nodes.append(node_to_process)

    path_from_nodes(from_tile, to_tile, closed_nodes)

if __name__ == "__main__":
    def random_map():
        tiles = create_map(50, 20)
        for tile in tiles:
            blocked = random.randint(0, 100) > 90
            if blocked:
                set_point(tiles, BLOCKED, tile["x"], tile["y"])

        set_point(tiles, START, random.randint(1, 49), random.randint(1, 19))
        set_point(tiles, FINISH, random.randint(1, 49), random.randint(1, 19))

        return tiles

    def test_map():
        tiles = create_map(7, 7)

        blocks = [
                    (2, 1),
                    (2, 2),
                    (3, 1),
                    (4, 1),
                    (4, 2),
                    (4, 3)
                  ]
        for block in blocks:
            set_point(tiles, BLOCKED, block[0], block[1])

        set_point(tiles, START, 1, 4)
        set_point(tiles, FINISH, 6, 2)

        return tiles

    tiles = random_map()
    astar(tiles)

    print_map(tiles)
