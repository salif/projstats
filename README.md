# projstats
Count source lines of code

## Install

### Arch Linux

- projstats is on [AUR](https://aur.archlinux.org/packages/projstats/)

### Other distributions

- [Download](https://github.com/salif/projstats/releases)
- `sudo make install`

### Other OS

- Build it yourself

## Usage

Go to folder, open terminal and run `projstats`

## Options

* `--cli` *Run without GUI*

* `--gui` *Run without CLI*

* `--wait` *Run without progressbar*

* `--list` *List files*

* `--list-skipped` *List skipped files and dirs*

* `--list-ext=ext` *List files with given extension*

* `--check-dir=dir` *Check if directory is skipped*

* `--check-file=file` *Check if file is skipped (absolute path)*

* `--skip-dir=dir` *Skip directory*

* `--skip-file=file` *Skip file (absolute path)*

* `--skip-ext=ext` *Skip files with given extension*

* `--no-ext` *Don't show extensions*

* `--help` *Print help*

## Example

`projstats --cli --list-skipped --skip-dir=META-INF`

```
scanning...
skipped: /home/salif/projstats/.git
skipped: /home/salif/projstats/src/META-INF
name: projstats
size: 13.22 KiB / 13.54 KB
files: 9
folders: 4
lines: 471
empty lines: 84
extensions: 
  java (6)
  [none] (1)
  gitignore (1)
  md (1)
```
