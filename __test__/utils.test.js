const {
  readInput,
  writeOutput,
  getFirstDigitOfLine,
  getLastDigitOfLine,
  main,
} = require('../src/utils');

const pathToInputFolder = 'input/';
const pathToOutputFolder = 'output/';

describe('readInput', () => {
  it('should read input file and return the content', async () => {
    const path = pathToInputFolder + 'input.test.txt';
    const expectedContent = '1abc2\npqr3stu8vwx\na1b2c3d4e5f\ntreb7uchet';

    const content = await readInput(path);

    expect(content).toEqual(expectedContent);
  });

  it('should throw an error if the input file does not exist', async () => {
    const path = pathToInputFolder + 'nonexistent.txt';

    const result = await readInput(path);

    expect(result).toBe(null);
  });
});

describe('writeOutput', () => {
  it('should write data to the output file', async () => {
    const path = pathToOutputFolder + 'output.test.txt';
    const data = 'Hello, World!';

    const result = await writeOutput(path, data);
    const resultData = await readInput(path);

    expect(result).toBe(true);
    expect(resultData).toEqual(data);
  });
});

describe('getFirstDigitOfLine', () => {
  it('should return the first digit of a line', () => {
    const line = 'e1abc2f';

    const result = getFirstDigitOfLine(line);

    expect(result).toEqual(1);
  });

  it('should return null if the line does not contain a digit', () => {
    const line = 'abc';

    const result = getFirstDigitOfLine(line);

    expect(result).toBe(null);
  });
});

describe('getLastDigitOfLine', () => {
  it('should return the last digit of a line', () => {
    const line = 'pqr3stu8vwx';

    const result = getLastDigitOfLine(line);

    expect(result).toEqual(8);
  });

  it('should return null if the line does not contain a digit', () => {
    const line = 'abc';

    const result = getLastDigitOfLine(line);

    expect(result).toBe(null);
  });
});

describe('main', () => {
  it('should read test input file and write result to output file', async () => {
    const inPath = pathToInputFolder + 'input.test.txt';
    const outPath = pathToOutputFolder + 'output.test.txt';

    await main(inPath, outPath);
    const content = await readInput(outPath);

    expect(content).toEqual('142');
  });
});
