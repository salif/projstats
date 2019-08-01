# projstats
Count source lines of code

## Install

### Arch Linux

- projstats is on [AUR](https://aur.archlinux.org/packages/projstats/)

### Other distributions

- [Download](https://github.com/salifm/projstats/releases)
- `sudo make install`

### Other OS

- Build it yourself

## Usage

Go to folder, open terminal and run `projstats`

## Options

### --cli

Run without GUI

### --gui

Run without CLI

### --wait

Run without progressbar

### --list

List files

### --list-skipped

List skipped files and dirs

### --check-dir=dir

Check if directory is skipped

### --check-file=file

Check if file is skipped [absolute path]

### --skip-dir=dir

Skip directory

### --skip-file=file

Skip file [absolute path]

### --help

Print help

## Example

`projstats --cli --list-skipped --skip-dir=out`

